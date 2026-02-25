package com.threekteckone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threekteckone.resorthub.models.OTP;

public interface OTPRepository extends JpaRepository<OTP, Integer> {
    
}
