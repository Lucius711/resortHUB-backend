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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
    @Builder.Default
    private BookingStatus status = BookingStatus.PENDING;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private LocalDateTime createdAt;

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

}
