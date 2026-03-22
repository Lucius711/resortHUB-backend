package com.threektechone.resorthub.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.payment.stripe")
public class StripePaymentProperties {

    private String secretKey = "";

    private String webhookSecret = "";

    private String successUrl = "http://localhost:3000/payment/stripe/success";

    private String cancelUrl = "http://localhost:3000/payment/stripe/cancel";
}
