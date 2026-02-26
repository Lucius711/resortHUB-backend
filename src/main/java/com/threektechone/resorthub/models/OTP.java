package com.threektechone.resorthub.models;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OTPS")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private int otpId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Boolean gender;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private String city;
    
    @Column(name = "otp_code", nullable = false, length = 10)
    private String otpCode;
    
    @CreationTimestamp
    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name="verified", nullable = true)
    private boolean verified = false;

    public OTP() {
    }

    public OTP(String email, String name, String password, String phone, Boolean gender, LocalDate dob, String city, String otpCode, LocalDateTime expiredAt, boolean verified) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.city = city;
        this.otpCode = otpCode;
        this.expiredAt = LocalDateTime.now().plusMinutes(5); // Set default expiry to 5 minutes from now
        this.verified = verified;
    }

    public int getOtpId() {
        return otpId;
    }

    public void setOtpId(int otpId) {
        this.otpId = otpId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }


    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "OTP{" +
                "otpId=" + otpId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", dob=" + dob +
                ", city='" + city + '\'' +
                ", otpCode='" + otpCode + '\'' +
                ", expiredAt=" + expiredAt +
                ", verified=" + verified +
                '}';
    }
}
