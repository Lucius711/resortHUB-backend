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

    Boolean existsByResortIdAndStatusIn(int resortId, List<BookingStatus> statuses);

    Boolean existsByResortIdAndPaymentStatusIn(int resortId, List<PaymentStatus> statuses);
    
}
