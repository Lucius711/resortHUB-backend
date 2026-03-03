package com.threektechone.resorthub.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.threektechone.resorthub.enums.UserStatus;

import jakarta.persistence.CascadeType;
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
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    
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

    @Column(name="status", nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    
    @CreationTimestamp
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner")
    private List<Resort> resorts;

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "reporter")
    private List<LostFoundItem> lostFoundItems;

    @OneToMany(mappedBy = "createdBy")
    private List<Contract> ASideContracts;

    @OneToMany(mappedBy = "relatedUser")
    private List<Contract> BSideContracts;

    @OneToMany(mappedBy = "sender")
    private List<ChatMessage> sentMessages;

    @OneToMany(mappedBy = "receiver")
    private List<ChatMessage> receivedMessages;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "user")
    private List<ResortReview> reviews;


    

    public User() {
    }

    public User(String fullName, String email, String password, String phone, Boolean gender, LocalDate dob,String image, String city, UserStatus status,Boolean isDeleted, Role role, LocalDateTime createdAt, List<Resort> resorts, List<Booking> bookings, List<LostFoundItem> lostFoundItems, List<Contract> ASideContracts, List<Contract> BSideContracts, List<ChatMessage> sentMessages, List<ChatMessage> receivedMessages, List<RefreshToken> refreshTokens) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.city = city;
        this.dob = dob;
        this.image = image;
        this.status = status;
        this.isDeleted = isDeleted;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.resorts = resorts;
        this.bookings = bookings;
        this.lostFoundItems = lostFoundItems;
        this.ASideContracts = ASideContracts;
        this.BSideContracts = BSideContracts;
        this.sentMessages = sentMessages;
        this.receivedMessages = receivedMessages;
        this.refreshTokens = refreshTokens;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Boolean isGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getImage() {
        return image;
    }
    
     public void setImage(String image) {
        this.image = image;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public List<Resort> getResorts() {
        return resorts;
    }

    public void setResorts(List<Resort> resorts) {
        this.resorts = resorts;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<ResortReview> getReviews() {
        return reviews;
    }

     public void setReviews(List<ResortReview> reviews) {
        this.reviews = reviews;
    }


    public List<LostFoundItem> getLostFoundItems() {
        return lostFoundItems;
    }

    public void setLostFoundItems(List<LostFoundItem> lostFoundItems) {
        this.lostFoundItems = lostFoundItems;
    }

    public List<Contract> getASideContracts() {
        return ASideContracts;
    }

    public void setASideContracts(List<Contract> ASideContracts) {
        this.ASideContracts = ASideContracts;
    }

    public List<Contract> getBSideContracts() {
        return BSideContracts;
    }

    public void setBSideContracts(List<Contract> BSideContracts) {
        this.BSideContracts = BSideContracts;
    }

    public List<ChatMessage> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<ChatMessage> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<ChatMessage> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<ChatMessage> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<RefreshToken> getRefreshTokens() {
        return refreshTokens;
    }

    public void setRefreshTokens(List<RefreshToken> refreshTokens) {
        this.refreshTokens = refreshTokens;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +'\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender + '\'' +
                ", dob=" + dob + '\'' +
                ", city='" + city + '\'' +
                ", status='" + status + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", role=" + role + '\''+
                ", createdAt=" + createdAt +'\''+
                ", resorts=" + resorts +'\''+
                ", bookings=" + bookings +'\''+
                ", lostFoundItems=" + lostFoundItems +'\''+
                ", ASideContracts=" + ASideContracts +'\''+
                ", BSideContracts=" + BSideContracts +'\''+
                ", sentMessages=" + sentMessages +'\''+
                ", receivedMessages=" + receivedMessages +'\''+
                ", refreshTokens=" + refreshTokens +
                '}';
    }

}