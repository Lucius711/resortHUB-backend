package com.threektechone.resorthub.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.threektechone.resorthub.models.Payment;
import com.threektechone.resorthub.repositories.projection.OwnerRevenueChartProjection;

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

    @Query("""
    SELECT 
        CAST(p.createdAt AS date) AS date,
        SUM(p.amount) AS revenue
    FROM Payment p
    WHERE p.booking.resort.owner.email = :ownerEmail
      AND p.paymentStatus = 'PAID'
      AND p.createdAt BETWEEN :fromDate AND :toDate
    GROUP BY CAST(p.createdAt AS date)
    ORDER BY CAST(p.createdAt AS date)
    """)
    List<OwnerRevenueChartProjection> getRevenueChart(
        String ownerEmail,
        LocalDateTime fromDate,
        LocalDateTime toDate
    );
}
