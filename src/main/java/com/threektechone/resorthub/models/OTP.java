package com.threektechone.resorthub.models;
import java.time.LocalDate;
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

    @Column(name="email",nullable = false,length=100)
    private String email;

    @Column(name = "full_name", nullable = false, length = 100)
    private String name;

    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name ="dob", nullable = false)
    private LocalDate dob;

    @Column(name="city", length = 50,nullable = false)
    private String city;
    
    @Column(name = "otp_code", nullable = false, length = 10)
    private String otpCode;
    
    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name="verified", nullable = true)
    @Builder.Default
    private boolean verified = false;
}
