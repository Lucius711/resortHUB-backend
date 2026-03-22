package com.threektechone.resorthub.service.CommonModule;

public interface BookingPaymentWebhookService {
    void handleStripeWebhook(String payload, String signatureHeader);
}
