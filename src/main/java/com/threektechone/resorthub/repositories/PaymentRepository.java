package com.threektechone.resorthub.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.threektechone.resorthub.models.Payment;

import jakarta.persistence.LockModeType;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByBooking_BookingId(int bookingId);

    Optional<Payment> findByGatewayReference(String gatewayReference);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Payment p WHERE p.paymentId = :id")
    Optional<Payment> lockById(@Param("id") int id);

    @Query("""
    SELECT COALESCE(SUM(p.amount), 0)
    FROM Payment p
    JOIN p.booking b
    JOIN b.resort r
    WHERE r.owner.email = :ownerEmail
      AND p.paymentStatus = 'PAID'
    """)
    double getTotalRevenue(@Param("ownerEmail") String ownerEmail);


    @Query("""
    SELECT COALESCE(SUM(p.amount), 0)
    FROM Payment p
    JOIN p.booking b
    JOIN b.resort r
    WHERE r.owner.email = :ownerEmail
      AND p.paymentStatus = 'PAID'
      AND MONTH(p.createdAt) = MONTH(CURRENT_DATE)
      AND YEAR(p.createdAt) = YEAR(CURRENT_DATE)
    """)
    double getRevenueThisMonth(@Param("ownerEmail") String ownerEmail);

    @Query("""
    SELECT COALESCE(SUM(p.amount), 0)
    FROM Payment p
    JOIN p.booking b
    JOIN b.resort r
    WHERE r.owner.email = :ownerEmail
      AND p.paymentStatus = 'PAID'
      AND MONTH(p.createdAt) = MONTH(CURRENT_DATE) - 1
      AND YEAR(p.createdAt) = YEAR(CURRENT_DATE)
    """)
    double getRevenueLastMonth(@Param("ownerEmail") String ownerEmail);
}
