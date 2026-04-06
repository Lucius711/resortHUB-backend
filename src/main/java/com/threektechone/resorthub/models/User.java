package com.threektechone.resorthub.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.UserStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name ="dob", nullable = false)
    private LocalDate dob;

    @Column(name="image",nullable=true)
    private String image;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phone", nullable = false, length = 20,unique = true)
    private String phone;

    @Column(name="city", length = 50,nullable = false)
    private String city;
    
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false, length = 20)
    private UserStatus status;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    
    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable=false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner")
    private List<Resort> resorts;

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "reporter")
    private List<LostFoundItem> lostFoundItems;

    @OneToMany(mappedBy = "owner")
    private List<Contract> ASideContracts;

    @OneToMany(mappedBy = "staff")
    private List<Contract> BSideContracts;

    @OneToMany(mappedBy = "sender")
    private List<ChatMessage> sentMessages;

    @OneToMany(mappedBy = "receiver")
    private List<ChatMessage> receivedMessages;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "user")
    private List<ResortReview> reviews;

    @OneToMany(mappedBy = "createdBy")
    private List<EditResortRequest> requests;
}