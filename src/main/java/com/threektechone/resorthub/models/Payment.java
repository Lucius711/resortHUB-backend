package com.threektechone.resorthub.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.PaymentGatewayProvider;
import com.threektechone.resorthub.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false,unique = true)
    private Booking booking;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false, length = 30)
    private String paymentMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "transaction_code", nullable = true, length = 100, unique = true)
    private String transactionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "gateway_provider", length = 20)
    private PaymentGatewayProvider gatewayProvider;

    @Column(name = "checkout_url", length = 2048)
    private String checkoutUrl;

    @Column(name = "gateway_reference", length = 255)
    private String gatewayReference;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable=false)
    private LocalDateTime createdAt;
}
