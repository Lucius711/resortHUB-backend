package com.threektechone.resorthub.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.threektechone.resorthub.repositories.OTPRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OTPScheduler {

    private final OTPRepository otpRepository;

    @Scheduled(fixedRate = 86400000) // mỗi 24 giờ
    public void cleanVerifiedOtp() {

        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);

        otpRepository.deleteByVerifiedTrueAndVerifiedAtBefore(cutoff);
    }
}