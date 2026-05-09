package com.threektechone.resorthub.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.OTP;

public interface OTPRepository extends JpaRepository<OTP, Integer> {
    Optional<OTP> findByEmailAndOtpCode(String email, String otpCode);

    Optional<OTP> findByEmail(String email);

    void deleteByVerifiedTrueAndVerifiedAtBefore(LocalDateTime cutoff);

}
