package com.threektechone.resorthub.strategy.booking;

import com.threektechone.resorthub.enums.PaymentGatewayProvider;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.Payment;

public interface BookingPaymentProviderStrategy {

    PaymentGatewayProvider getProvider();

    String createCheckoutUrl(Payment payment, Booking booking, String clientIp);
}
