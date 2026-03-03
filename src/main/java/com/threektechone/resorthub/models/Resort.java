package com.threektechone.resorthub.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.ResortStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @Column(name = "max_guests", nullable = false)
    private int maxGuest;

    @Column(name = "rating", precision = 2, scale = 1)
    private BigDecimal averageRating;

    @OneToMany(mappedBy = "resort")
    private List<ResortReview> reviews;

    @ManyToMany
    @JoinTable(name = "resort_amenities",joinColumns = @JoinColumn(name = "resort_id"),inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set<ResortAmenity> amenities;

    @OneToMany(mappedBy = "resort", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResortMenu> menuItems;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "resort",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ResortImage> images = new ArrayList<>();

    @Column(name = "status", nullable = false, length = 20)
    private ResortStatus status = ResortStatus.PENDING;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "resort")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "resort")
    private List<Contract> contracts;


    public Resort() {
        this.amenities = new HashSet<>();
    }

    public Resort(User owner, String name, String description,int maxGuest,BigDecimal averageRating, String location, BigDecimal price,List<ResortReview> reviews,Set<ResortAmenity> amenities,List<ResortMenu> menuItems,List<ResortImage> images, ResortStatus status, LocalDateTime createdAt, List<Booking> bookings, List<Contract> contracts) {
        this.amenities = new HashSet<>();
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.location = location;
        this.maxGuest = maxGuest;
        this.price = price;
        this.amenities = amenities;
        this.averageRating = averageRating;
        this.menuItems = menuItems;
        this.reviews = reviews;
        this.images = images;
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
    public int getMaxGuest() {
        return maxGuest;
    }
    public void setMaxGuest(int maxGuest) {
        this.maxGuest = maxGuest;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ResortImage> getImages() {
        return images;
    }

    public void setImages(List<ResortImage> images) {
        this.images = images;
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
    public List<ResortReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<ResortReview> reviews) {
        this.reviews = reviews;
    }

    public Set<ResortAmenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<ResortAmenity> amenities) {
        this.amenities = amenities;
    }

    public List<ResortMenu> getMenuItems() {
        return menuItems;
    }

     public void setMenuItems(List<ResortMenu> menuItems) {
        this.menuItems = menuItems;
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