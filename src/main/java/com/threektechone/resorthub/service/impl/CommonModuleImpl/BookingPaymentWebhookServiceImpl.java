package com.threektechone.resorthub.service.impl.CommonModuleImpl;

import org.springframework.stereotype.Service;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.threektechone.resorthub.common.exception.custom.InvalidPaymentException;
import com.threektechone.resorthub.config.properties.StripePaymentProperties;
import com.threektechone.resorthub.enums.PaymentGatewayProvider;
import com.threektechone.resorthub.enums.PaymentStatus;
import com.threektechone.resorthub.models.Payment;
import com.threektechone.resorthub.repositories.PaymentRepository;
import com.threektechone.resorthub.service.CommonModule.BookingPaymentWebhookService;
import com.threektechone.resorthub.strategy.StripeBookingPaymentProvider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingPaymentWebhookServiceImpl implements BookingPaymentWebhookService {

    private final StripePaymentProperties stripePaymentProperties;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void handleStripeWebhook(String payload, String signatureHeader) {
        log.info("=== STRIPE WEBHOOK START ===");
        log.info("Signature: {}", signatureHeader);
        if (stripePaymentProperties.getWebhookSecret() == null
                || stripePaymentProperties.getWebhookSecret().isBlank()) {
            throw new InvalidPaymentException("Stripe webhook secret is not configured");
        }

        Event event;
        try {
            event = Webhook.constructEvent(payload, signatureHeader, stripePaymentProperties.getWebhookSecret().trim());
            log.info("Event type: {}", event.getType());
        } catch (SignatureVerificationException e) {
            throw new InvalidPaymentException("Invalid Stripe signature");
        }

        if (!"checkout.session.completed".equals(event.getType())) {
            return;
        }

        Session eventSession = (Session) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new InvalidPaymentException("Stripe webhook payload missing session"));

        String sessionId = eventSession.getId();
        if (sessionId == null || sessionId.isBlank()) {
            throw new InvalidPaymentException("Stripe webhook missing checkout session id");
        }

        log.info("Event Session ID: {}", eventSession.getId());
        log.info("Event Metadata (raw): {}", eventSession.getMetadata());

        // Webhook payloads are often thin: metadata / amount_total may be missing on the embedded object.
        // Always reload the session from the Stripe API so metadata and amounts match Checkout.
        Session session;
        try {
            session = Session.retrieve(sessionId);
        } catch (StripeException e) {
            log.error("Stripe Session.retrieve failed sessionId={} : {}", sessionId, e.getMessage());
            throw new InvalidPaymentException("Failed to load Stripe session: " + e.getMessage());
        }

        log.info("Retrieved Session ID: {}", session.getId());
        log.info("Retrieved Metadata: {}", session.getMetadata());
        log.info("Stripe Amount: {}", session.getAmountTotal());
        log.info("Stripe Payment Status: {}", session.getPaymentStatus());

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
            log.warn("Stripe session amount_total is null sessionId={}", sessionId);
            throw new InvalidPaymentException("Stripe session missing amount total");
        }
        if (!expectedAmountEquals(amountTotal, expectedAmount)) {
            log.warn("Stripe amount mismatch sessionId={} stripeTotal={} expected={}", sessionId, amountTotal, expectedAmount);
            throw new InvalidPaymentException("Stripe amount mismatch");
        }
        log.info("Compare amount → Stripe: {} | Expected(DB): {}",
        amountTotal, expectedAmount);

        String txn = session.getPaymentIntent();
        if (txn == null || txn.isBlank()) {
            txn = session.getId();
        }

        payment.setTransactionCode(txn);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        log.info("Stripe payment marked SUCCESS paymentId={} sessionId={}", payment.getPaymentId(), sessionId);
        paymentRepository.save(payment);
    }

    private boolean expectedAmountEquals(Long reported, long expected) {
        return reported != null && reported == expected;
    }
}
