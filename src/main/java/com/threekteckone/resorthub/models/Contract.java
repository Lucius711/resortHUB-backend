package com.threekteckone.resorthub.models;
import java.time.LocalDate;

import com.threekteckone.resorthub.enums.ContractType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private int contractId;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "related_to", nullable = false)
    private User relatedUser;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "resort_id", nullable = false)
    private Resort resort;

    @Column(name = "contract_type", nullable = false, length = 30)
    private ContractType contractType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public Contract() {
    }
    public Contract(User createdBy, User relatedUser, Booking booking, Resort resort, ContractType contractType, LocalDate startDate, LocalDate endDate) {
        this.createdBy = createdBy;
        this.relatedUser = relatedUser;
        this.booking = booking;
        this.resort = resort;
        this.contractType = contractType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getRelatedUser() {
        return relatedUser;
    }


    public void setRelatedUser(User relatedUser) {
        this.relatedUser = relatedUser;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Resort getResort() {
        return resort;
    }

    public void setResort(Resort resort) {
        this.resort = resort;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    @Override
    public String toString() {
        return "Contract{" +
                "contractId=" + contractId +
                ", createdBy=" + createdBy +
                ", relatedUser=" + relatedUser +
                ", booking=" + booking +
                ", resort=" + resort +
                ", contractType=" + contractType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }


    
}
