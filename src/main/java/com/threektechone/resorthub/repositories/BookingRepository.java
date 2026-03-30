package com.threektechone.resorthub.repositories;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.enums.PaymentStatus;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.repositories.projection.OwnerBookingChartProjection;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
    SELECT b FROM Booking b
    WHERE b.customer.email = :email 
    AND (:searchkey IS NULL OR 
         LOWER(b.bookingCode) LIKE LOWER(CONCAT('%', :searchkey, '%')) 
         OR LOWER(b.resort.name) LIKE LOWER(CONCAT('%', :searchkey, '%')))
    AND (:status IS NULL OR b.status = :status)
    """)
    Page<Booking> getCustomerBookings(
        @Param("email") String email,
        @Param("searchkey") String searchkey,
        @Param("status") BookingStatus status,
        Pageable pageable
    );

    @Query("""
        SELECT b FROM Booking b
        WHERE b.status = 'PENDING'
        AND b.expiredAt < :now
    """)
    List<Booking> findExpiredBookings(LocalDateTime now);

    @Query("""
    SELECT b FROM Booking b
    WHERE b.resort.owner.email = :email
    AND (:searchkey IS NULL OR 
         LOWER(b.bookingCode) LIKE LOWER(CONCAT('%', :searchkey, '%')) 
         OR LOWER(b.customer.fullName) LIKE LOWER(CONCAT('%', :searchkey, '%')))
    AND (:status IS NULL OR b.status = :status)
    """)
    Page<Booking> getOwnerBookings(
        @Param("email") String email,
        @Param("searchkey") String searchkey,
        @Param("status") BookingStatus status,
        Pageable pageable
    );

    @Query("""
    SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
    FROM Booking b
    WHERE b.resort.resortId = :resortId
    AND b.status = 'CHECKED_IN'
    """)
    Boolean isRoomOccupied(@Param("resortId") int resortId);

    @Query("""
    SELECT COUNT(b) > 0
    FROM Booking b
    WHERE b.resort.resortId = :resortId
    AND b.status IN :statuses
    """)
    boolean existsActiveBooking(
    @Param("resortId") int resortId,
    @Param("statuses") List<BookingStatus> statuses
    );

    @Query("""
    SELECT COUNT(b) > 0
    FROM Booking b
    WHERE b.resort.resortId = :resortId
    AND b.payment.paymentStatus IN :statuses
    """)
    boolean existsUnfinishedPayment(
        @Param("resortId") int resortId,
        @Param("statuses") List<PaymentStatus> statuses
    );

    @Query("""
    SELECT COUNT(b)
    FROM Booking b
    JOIN b.resort r
    WHERE r.owner.email = :ownerEmail
    """)
    int getTotalBookings(@Param("ownerEmail") String ownerEmail);

    @Query("""
    SELECT COUNT(b)
    FROM Booking b
    JOIN b.resort r
    WHERE r.owner.email = :ownerEmail
      AND MONTH(b.createdAt) = MONTH(CURRENT_DATE)
      AND YEAR(b.createdAt) = YEAR(CURRENT_DATE)
    """)
   int getBookingThisMonth(@Param("ownerEmail") String ownerEmail);


   @Query("""
    SELECT 
        CAST(b.createdAt AS date) AS date,
        COUNT(b) AS totalBookings
    FROM Booking b
    WHERE b.resort.owner.email = :ownerEmail
      AND b.status IN ('APPROVED', 'COMPLETED')
      AND b.createdAt BETWEEN :fromDate AND :toDate
    GROUP BY CAST(b.createdAt AS date)
    ORDER BY CAST(b.createdAt AS date)
    """)
    List<OwnerBookingChartProjection> getBookingChart(
        String ownerEmail,
        LocalDateTime fromDate,
        LocalDateTime toDate
    );
}
