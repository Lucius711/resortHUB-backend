package com.threektechone.resorthub.models;
import java.time.LocalDate;

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


@Entity
@Table(name = "Contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private int contractId;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private User staff;

    @ManyToOne
    @JoinColumn(name = "resort_id", nullable = false)
    private Resort resort;

    @Column(name = "contract_type", nullable = false, length = 30)
    private ContractType contractType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ContractStatus status;

    public Contract() {
    }
    public Contract(User owner, User staff, Resort resort, ContractType contractType, LocalDate startDate, LocalDate endDate,ContractStatus status) {
        this.owner = owner;
        this.staff = staff;
        this.resort = resort;
        this.contractType = contractType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getStaff() {
        return staff;
    }


    public void setStaff(User staff) {
        this.staff = staff;
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

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Contract{" +
                "contractId=" + contractId +
                ", owner=" + owner +
                ", staff=" + staff +
                ", resort=" + resort +
                ", contractType=" + contractType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }


    
}
