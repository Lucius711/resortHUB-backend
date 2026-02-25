package com.threekteckone.resorthub.models;

import java.time.LocalDateTime;
import java.util.List;

import com.threekteckone.resorthub.enums.UserStatus;

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

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phone", nullable = false, length = 20,unique = true)
    private String phone;

    @Column(name="status", nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

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

    @OneToMany(mappedBy = "user")
    private List<OTP> otps;

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> refreshTokens;

    public User() {
    }

    public User(String fullName, String email, String password, String phone, UserStatus status, Role role, LocalDateTime createdAt, List<Resort> resorts, List<Booking> bookings, List<LostFoundItem> lostFoundItems, List<Contract> ASideContracts, List<Contract> BSideContracts, List<ChatMessage> sentMessages, List<ChatMessage> receivedMessages, List<OTP> otps, List<RefreshToken> refreshTokens) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.status = status;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.resorts = resorts;
        this.bookings = bookings;
        this.lostFoundItems = lostFoundItems;
        this.ASideContracts = ASideContracts;
        this.BSideContracts = BSideContracts;
        this.sentMessages = sentMessages;
        this.receivedMessages = receivedMessages;
        this.otps = otps;
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
    
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
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

    public List<OTP> getOtps() {
        return otps;
    }

    public void setOtps(List<OTP> otps) {
        this.otps = otps;
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
                "user_id=" + user_id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", resorts=" + resorts +
                ", bookings=" + bookings +
                ", lostFoundItems=" + lostFoundItems +
                ", ASideContracts=" + ASideContracts +
                ", BSideContracts=" + BSideContracts +
                ", sentMessages=" + sentMessages +
                ", receivedMessages=" + receivedMessages +
                ", otps=" + otps +
                ", refreshTokens=" + refreshTokens +
                '}';
    }
}