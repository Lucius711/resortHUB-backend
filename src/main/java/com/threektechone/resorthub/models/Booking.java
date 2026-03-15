package com.threektechone.resorthub.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.BookingStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Booking {

    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "resort_id", nullable = false)
    private Resort resort;
    
    @OneToOne
    @JoinColumn(name = "payment_id",unique = true)
    private Payment payment;

    @Column(name= "booking_code", nullable=false)
    private String bookingCode;

    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDateTime checkOutDate;

    @Column(name="number_of_person",nullable=false)
    private int numberOfPerson;
    
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false, length = 20)
    private BookingStatus status;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expired_at", nullable = false,updatable = false)
    private LocalDateTime expiredAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @OneToMany(mappedBy = "booking")
    private List<LostFoundItem> lostFoundItems;

    @OneToMany(mappedBy= "booking", cascade = CascadeType.ALL)
    private List<BookingMeal> meals;
    
    @Column(name = "accepted_terms")
    private Boolean acceptedTerms;

    @Column(name= "total_price")
    private BigDecimal totalPrice;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "reason", length=255)
    private String reason;

    @PrePersist
    public void setExpireTime() {
        this.expiredAt = LocalDateTime.now().plusHours(24);
    }

}
