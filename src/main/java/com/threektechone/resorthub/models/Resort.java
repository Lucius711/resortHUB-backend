package com.threektechone.resorthub.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.threektechone.resorthub.enums.ResortStatus;

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
@Table(name = "Resorts")
public class Resort {
    @Id
    @Column(name = "resort_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resort_id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "status", nullable = false, length = 20)
    private ResortStatus status = ResortStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "resort")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "resort")
    private List<Contract> contracts;


    public Resort() {
    }

    public Resort(User owner, String name, String description, String location, BigDecimal price, ResortStatus status, LocalDateTime createdAt, List<Booking> bookings, List<Contract> contracts) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.location = location;
        this.price = price;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.bookings = bookings;
        this.contracts = contracts;
    }

    public int getResortId() {
        return resort_id;
    }

    public void setResortId(int resort_id) {
        this.resort_id = resort_id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ResortStatus getStatus() {
        return status;
    }

    public void setStatus(ResortStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return "Resort{" +
                "resort_id=" + resort_id +
                ", owner=" + owner +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", bookings=" + bookings +
                ", contracts=" + contracts +
                '}';
    }

    
}