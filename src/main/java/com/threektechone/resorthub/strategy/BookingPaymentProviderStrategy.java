package com.threektechone.resorthub.strategy;

import com.threektechone.resorthub.enums.PaymentGatewayProvider;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.Payment;

public interface BookingPaymentProviderStrategy {

    PaymentGatewayProvider getProvider();

    /**
     * Calls the provider API and updates {@code payment} (checkout URL, gateway reference, provider).
     *
     * @param clientIp used for VNPay vnp_IpAddr
     * @return redirect URL for the customer browser
     */
    String createCheckoutUrl(Payment payment, Booking booking, String clientIp);
}
