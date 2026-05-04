package com.threektechone.resorthub.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.RequestStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "edit_request")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EditResortRequest {

    @Id
    @Column(name="request_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int requestId;

    @ManyToOne
    @JoinColumn(name="resort_id",nullable=false)
    private Resort resort;

    @Column(name="old_data",columnDefinition="TEXT",nullable=false)
    private String oldData;

    @Column(name="new_data",columnDefinition="TEXT",nullable=false)
    private String newData;

    @Enumerated(EnumType.STRING)
    @Column(name="request_status",nullable=false)
    private RequestStatus requestStatus;
    
    @ManyToOne
    @JoinColumn(name="owner_id",nullable=false)
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable=false)
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name="staff_id",nullable=false)
    private User approvedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name="note",columnDefinition="TEXT",nullable=true)
    private String note;
}
