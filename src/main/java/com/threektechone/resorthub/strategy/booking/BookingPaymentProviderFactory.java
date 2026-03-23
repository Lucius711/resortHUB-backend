package com.threektechone.resorthub.strategy.booking;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.common.exception.custom.InvalidPaymentException;
import com.threektechone.resorthub.enums.PaymentGatewayProvider;

@Component
public class BookingPaymentProviderFactory {

    private final Map<PaymentGatewayProvider, BookingPaymentProviderStrategy> strategies = new EnumMap<>(PaymentGatewayProvider.class);

    public BookingPaymentProviderFactory(List<BookingPaymentProviderStrategy> strategyList) {
        for (BookingPaymentProviderStrategy s : strategyList) {
            strategies.put(s.getProvider(), s);
        }
    }

    public BookingPaymentProviderStrategy get(PaymentGatewayProvider provider) {
        BookingPaymentProviderStrategy s = strategies.get(provider);
        if (s == null) {
            throw new InvalidPaymentException("No strategy for provider: " + provider);
        }
        return s;
    }
}
