package com.threektechone.resorthub.models;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.ContractStatus;
import com.threektechone.resorthub.enums.ContractType;

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
@Table(name = "Contracts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private int contractId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private User staff;

    @ManyToOne
    @JoinColumn(name = "resort_id", nullable = false)
    private Resort resort;

    @Column(name = "contract_type", nullable = false, length = 30)
    private ContractType contractType;

    @Column(name = "contract_file",length=255)
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ContractStatus status;
    
    @Column(name="signed_by_owner")
    private Boolean signedByOwner;

    @Column(name= "accepted_terms")
    private Boolean acceptedTerms;
    
    @Column(name= "signed_at")
    private LocalDateTime signedAt;

    @CreationTimestamp
    @Column(name= "created_at", nullable=false,updatable=false)
    private LocalDateTime createdAt;
    
}
