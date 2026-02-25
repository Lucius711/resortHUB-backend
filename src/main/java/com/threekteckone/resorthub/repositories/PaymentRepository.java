package com.threekteckone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threekteckone.resorthub.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
}
