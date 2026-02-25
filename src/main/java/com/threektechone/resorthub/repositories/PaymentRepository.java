package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
}
