package com.threektechone.resorthub.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.OTP;

public interface OTPRepository extends JpaRepository<OTP, Integer> {
    Optional<OTP> findByEmailAndOtpCode(String email, String otpCode);
    
}
