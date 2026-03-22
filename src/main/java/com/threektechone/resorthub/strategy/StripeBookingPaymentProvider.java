package com.threektechone.resorthub.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.threektechone.resorthub.common.exception.custom.InvalidPaymentException;
import com.threektechone.resorthub.config.properties.StripePaymentProperties;
import com.threektechone.resorthub.enums.PaymentGatewayProvider;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.Payment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StripeBookingPaymentProvider {

    private final StripePaymentProperties properties;

    @PostConstruct
    @SuppressWarnings("unused")
    void initStripeKey() {
        if (properties.getSecretKey() != null && !properties.getSecretKey().isBlank()) {
            Stripe.apiKey = properties.getSecretKey().trim();
        }
    }

    /**
     * VND is a zero-decimal currency on Stripe: unit amount is whole VND.
     * Must use the same rounding as {@code BookingPaymentWebhookServiceImpl} when comparing amount_total.
     */
    public static long toStripeVndUnitAmount(BigDecimal amount) {
        return amount.setScale(0, RoundingMode.HALF_UP).longValue();
    }

    public String createCheckoutUrl(Payment payment, Booking booking, String clientIp) {
        if (properties.getSecretKey() == null || properties.getSecretKey().isBlank()) {
            throw new InvalidPaymentException("Stripe is not configured (app.payment.stripe.secret-key)");
        }
        long unitAmount = toStripeVndUnitAmount(payment.getAmount());
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(properties.getSuccessUrl())
                    .setCancelUrl(properties.getCancelUrl())
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .putMetadata("paymentId", String.valueOf(payment.getPaymentId()))
                    .putMetadata("bookingId", String.valueOf(booking.getBookingId()))
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("vnd")
                                                    .setUnitAmount(unitAmount)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Booking " + booking.getBookingCode())
                                                                    .build())
                                                    .build())
                                    .build())
                    .build();

            Session session = Session.create(params);
            payment.setGatewayProvider(PaymentGatewayProvider.STRIPE);
            payment.setPaymentMethod("STRIPE");
            payment.setGatewayReference(session.getId());
            payment.setCheckoutUrl(session.getUrl());
            log.info("Stripe Checkout session created bookingId={} paymentId={} sessionId={}",
                    booking.getBookingId(), payment.getPaymentId(), session.getId());
            return session.getUrl();
        } catch (StripeException e) {
            log.error("Stripe session error: {}", e.getMessage());
            throw new InvalidPaymentException("Stripe checkout failed: " + e.getMessage());
        }
    }
}
