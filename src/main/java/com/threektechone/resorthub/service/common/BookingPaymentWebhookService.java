package com.threektechone.resorthub.service.common;

public interface BookingPaymentWebhookService {
    void handleStripeWebhook(String payload, String signatureHeader);
}
