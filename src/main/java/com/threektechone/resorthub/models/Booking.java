package com.threektechone.resorthub.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Bookings")
public class Booking {

    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;


    @ManyToOne
    @JoinColumn(name = "resort_id", nullable = false)
    private Resort resort;

    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDateTime checkOutDate;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Column(name="status", nullable = false, length = 20)
    private BookingStatus status = BookingStatus.PENDING;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "booking")
    private List<LostFoundItem> lostFoundItems;
    
    @Column(name = "accepted_terms", nullable = false)
    private Boolean acceptedTerms;

    @CreationTimestamp
    @Column(name = "accepted_at",nullable=false)
    private LocalDateTime acceptedAt;

    public Booking() {
    }

    public Booking(User customer, Resort resort, LocalDateTime checkInDate, LocalDateTime checkOutDate, BigDecimal totalPrice, BookingStatus status, LocalDateTime createdAt, List<LostFoundItem> lostFoundItems,Boolean acceptedTerms, LocalDateTime acceptedAt ) {
        this.customer = customer;
        this.resort = resort;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.lostFoundItems = lostFoundItems;
        this.acceptedTerms  = acceptedTerms;
        this.acceptedAt = acceptedAt;
    }

    public int getBookingId() {
        return booking_id;
    }

    public void setBookingId(int booking_id) {
        this.booking_id = booking_id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
    
    public Resort getResort() {
        return resort;
    }

    public void setResort(Resort resort) {
        this.resort = resort;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }


    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
     
    public List<LostFoundItem> getLostFoundItems() {
        return lostFoundItems;
    }

    public void setLostFoundItems(List<LostFoundItem> lostFoundItems) {
        this.lostFoundItems = lostFoundItems;
    }
    
    public Boolean isAcceptedTerms() {
        return acceptedTerms;
    }

    public void setAcceptedTerms(Boolean acceptedTerms) {
        this.acceptedTerms = acceptedTerms;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    } 

    @Override
    public String toString() {
        return "Booking{" +
                "booking_id=" + booking_id +
                ", customer=" + customer +
                ", resort=" + resort +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", acceptedTerms=" + acceptedTerms +
                ", acceptedAt=" + acceptedAt +
                '}';
    }
}
