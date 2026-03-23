package com.threektechone.resorthub.dto.customer;

import java.math.BigDecimal;

import com.threektechone.resorthub.enums.PaymentGatewayProvider;
import com.threektechone.resorthub.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingCreatedResponseDTO {
    private int bookingId;
    private String bookingCode;
    private BigDecimal totalAmount;
    private PaymentGatewayProvider paymentProvider;
    private PaymentStatus paymentStatus;
    private String paymentUrl;
}
