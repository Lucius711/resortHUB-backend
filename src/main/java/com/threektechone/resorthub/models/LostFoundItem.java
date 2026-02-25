package com.threektechone.resorthub.models;

import java.time.LocalDateTime;

import com.threektechone.resorthub.enums.LostFoundItemStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LostFoundItems")
public class LostFoundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportId;
    
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "reporter_by", nullable = false)
    private User reporter;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "status", nullable = true, length = 20)
    private LostFoundItemStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LostFoundItem() {
    }

    public LostFoundItem(Booking booking, User reporter, String description, LostFoundItemStatus status, LocalDateTime createdAt) {
        this.booking = booking;
        this.reporter = reporter;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public LostFoundItemStatus getStatus() {
        return status;
    }

    public void setStatus(LostFoundItemStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LostFoundItem{" +
                "reportId=" + reportId +
                ", booking=" + booking +
                ", reporter=" + reporter +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
