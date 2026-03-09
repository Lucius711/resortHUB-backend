package com.threektechone.resorthub.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.ResortRegistrationStep;
import com.threektechone.resorthub.enums.ResortStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Resorts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Resort {
    @Id
    @Column(name = "resort_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resortId;

    @Column(name = "resort_code", unique = true, nullable = false)
    private String resortCode;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name="staff_id",nullable=true)
    private User staff;

    @Column(name = "name", nullable = true, length = 150)
    private String name;

    @Column(name = "description", nullable = true, length = 255)
    private String description;
    
    @Column(name = "city", length = 255)
    private String city;
    
    @Column(name = "district", length = 255)
    private String district;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "max_guests")
    private int maxGuest;

    @Column(name = "rating", precision = 2, scale = 1)
    private BigDecimal averageRating;

    @Column(name = "reason",length=255,nullable=true)
    private String reason;

    @OneToMany(mappedBy = "resort")
    private List<ResortReview> reviews;

    @ManyToMany
    @JoinTable(name = "resort_amenity_map",joinColumns = @JoinColumn(name = "resort_id"),inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set<ResortAmenity> amenities;

    @OneToMany(mappedBy = "resort", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResortMenu> menuItems;

    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "resort",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ResortImage> images;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ResortStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "register_step", nullable = false, length = 20)
    private ResortRegistrationStep step;
    
    @CreationTimestamp
    @Column(name = "created_at",updatable=false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "resort")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "resort")
    private List<Contract> contracts;

    @OneToMany(mappedBy= "resort")
    private List<EditResortRequest> requests;
    
}