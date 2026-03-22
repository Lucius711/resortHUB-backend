package com.threektechone.resorthub.config.paymentgateway;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.threektechone.resorthub.config.properties.StripePaymentProperties;

@Configuration
@EnableConfigurationProperties({ StripePaymentProperties.class })
public class PaymentGatewayConfiguration {
}
