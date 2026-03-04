package com.threektechone.resorthub.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.LostFoundItemStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LostFoundItems")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable=false)
    private LocalDateTime createdAt;

}
