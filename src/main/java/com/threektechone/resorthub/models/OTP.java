package com.threektechone.resorthub.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OTPS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private int otpId;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "otp_code", nullable = false, length = 10)
    private String otpCode;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "verified", nullable = true)
    @Builder.Default
    private boolean verified = false;

    @Column(name = "verified_at", nullable = true)
    private LocalDateTime verifiedAt;
}
