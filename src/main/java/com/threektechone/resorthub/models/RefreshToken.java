package com.threektechone.resorthub.models;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "token", nullable = false, length = 500, unique = true)
    private String token;

    @Column(name = "expiryDate", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "is_revoked", nullable = true)
    private boolean isRevoked = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    

    public RefreshToken() {
    }

    public RefreshToken(User user, String token, LocalDateTime expiryDate, boolean isRevoked) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusDays(7); // Set default expiry to 7 days from now
        this.isRevoked = isRevoked;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isRevoked() {
        return isRevoked;
    }

    public void setRevoked(boolean revoked) {
        isRevoked = revoked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                ", isRevoked=" + isRevoked +
                ", createdAt=" + createdAt +
                '}';
    }

    
}
