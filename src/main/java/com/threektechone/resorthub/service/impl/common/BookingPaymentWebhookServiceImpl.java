package com.threektechone.resorthub.service.impl.common;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.threektechone.resorthub.common.exception.custom.InvalidPaymentException;
import com.threektechone.resorthub.config.properties.StripePaymentProperties;
import com.threektechone.resorthub.enums.PaymentGatewayProvider;
import com.threektechone.resorthub.enums.PaymentStatus;
import com.threektechone.resorthub.models.Payment;
import com.threektechone.resorthub.repositories.PaymentRepository;
import com.threektechone.resorthub.service.common.BookingPaymentWebhookService;
import com.threektechone.resorthub.strategy.booking.StripeBookingPaymentProvider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingPaymentWebhookServiceImpl implements BookingPaymentWebhookService {

    private final StripePaymentProperties stripePaymentProperties;
    private final PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void handleStripeWebhook(String payload, String signatureHeader) {
        if (stripePaymentProperties.getWebhookSecret() == null
                || stripePaymentProperties.getWebhookSecret().isBlank()) {
            throw new InvalidPaymentException("Stripe webhook secret is not configured");
        }

        try {
            Event event = Webhook.constructEvent(
                    payload,
                    signatureHeader,
                    stripePaymentProperties.getWebhookSecret().trim());

            String eventType = event.getType() != null ? event.getType().trim() : "";
            if (!"checkout.session.completed".equals(eventType)) {
                log.debug("Ignoring Stripe event type: {}", eventType);
                return;
            }

            handleCheckoutSessionCompleted(event, payload);
        } catch (SignatureVerificationException e) {
            log.error("Stripe signature verification failed: {}", e.getMessage());
            throw new InvalidPaymentException("Invalid Stripe signature");
        }
    }

    /**
     * Stripe Java SDK often returns an empty {@code getObject()} for webhook snapshots (API version / thin payloads).
     * We resolve {@code cs_...} from the raw HTTP body or {@link com.stripe.model.Event.DataObjectDeserializer#getRawJson()},
     * then load the full session via the API.
     */
    private void handleCheckoutSessionCompleted(Event event, String rawPayload) {
        String sessionId = resolveCheckoutSessionId(event, rawPayload);
        if (sessionId == null || sessionId.isBlank()) {
            throw new InvalidPaymentException("Stripe webhook payload missing session");
        }

        Session session;
        try {
            session = Session.retrieve(sessionId);
        } catch (StripeException e) {
            log.error("Stripe Session.retrieve failed sessionId={}: {}", sessionId, e.getMessage());
            throw new InvalidPaymentException("Failed to load Stripe session: " + e.getMessage());
        }

        String paymentIdStr = session.getMetadata() != null ? session.getMetadata().get("paymentId") : null;

        Payment payment;
        if (paymentIdStr != null && !paymentIdStr.isBlank()) {
            int paymentId = Integer.parseInt(paymentIdStr);
            payment = paymentRepository.lockById(paymentId)
                    .orElseThrow(() -> new InvalidPaymentException("Payment not found"));
        } else {
            Payment found = paymentRepository.findByGatewayReference(sessionId)
                    .orElseThrow(() -> new InvalidPaymentException("Payment not found for checkout session"));
            payment = paymentRepository.lockById(found.getPaymentId())
                    .orElseThrow(() -> new InvalidPaymentException("Payment not found"));
        }

        if (payment.getGatewayReference() != null && !payment.getGatewayReference().equals(sessionId)) {
            throw new InvalidPaymentException("Checkout session does not match payment record");
        }

        if (payment.getGatewayProvider() != PaymentGatewayProvider.STRIPE) {
            throw new InvalidPaymentException("Payment provider mismatch for Stripe webhook");
        }

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            return;
        }

        long expectedAmount = StripeBookingPaymentProvider.toStripeVndUnitAmount(payment.getAmount());
        Long amountTotal = session.getAmountTotal();
        if (amountTotal == null) {
            throw new InvalidPaymentException("Stripe session missing amount total");
        }
        if (!amountTotal.equals(expectedAmount)) {
            log.warn("Stripe amount mismatch sessionId={} stripeTotal={} expected={}", sessionId, amountTotal, expectedAmount);
            throw new InvalidPaymentException("Stripe amount mismatch");
        }

        String txn = session.getPaymentIntent();
        if (txn == null || txn.isBlank()) {
            txn = session.getId();
        }

        payment.setTransactionCode(txn);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        log.info("Stripe payment marked SUCCESS paymentId={} sessionId={}", payment.getPaymentId(), sessionId);
    }

    private String resolveCheckoutSessionId(Event event, String rawPayload) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

        if (deserializer.getObject().isPresent()) {
            StripeObject obj = deserializer.getObject().get();
            if (obj instanceof Session eventSession) {
                String id = eventSession.getId();
                if (id != null && !id.isBlank()) {
                    return id;
                }
            }
        }

        try {
            JsonNode root = objectMapper.readTree(rawPayload);
            JsonNode idNode = root.path("data").path("object").path("id");
            if (idNode.isTextual()) {
                String id = idNode.asText();
                if (id.startsWith("cs_")) {
                    log.debug("Resolved checkout session id from raw webhook JSON");
                    return id;
                }
            }
        } catch (JsonProcessingException e) {
            log.warn("Could not parse session id from webhook body: {}", e.getMessage());
        }

        try {
            String rawJson = deserializer.getRawJson();
            if (rawJson != null && !rawJson.isBlank()) {
                JsonNode node = objectMapper.readTree(rawJson);
                if (node.has("id")) {
                    String id = node.get("id").asText();
                    if (id.startsWith("cs_")) {
                        log.debug("Resolved checkout session id from EventDataObjectDeserializer raw JSON");
                        return id;
                    }
                }
            }
        } catch (JsonProcessingException e) {
            log.warn("Could not parse session id from deserializer raw JSON: {}", e.getMessage());
        }

        log.error("Stripe webhook: getObject() empty and could not parse session id from payload");
        return null;
    }
}
