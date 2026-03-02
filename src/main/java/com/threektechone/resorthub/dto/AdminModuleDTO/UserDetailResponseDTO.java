package com.threektechone.resorthub.dto.AdminModuleDTO;

import java.time.LocalDate;

import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;

public class UserDetailResponseDTO {
    private int userid;
    private String fullName;
    private String email;
    private Boolean gender;
    private LocalDate dob;
    private String phone;
    private String city;
    private UserStatus status;
    private RoleName roleName;
    
    public UserDetailResponseDTO(){}


    public UserDetailResponseDTO(int userid, String fullName, String email, Boolean gender,
                                LocalDate dob, String phone, String city,
                                UserStatus status, RoleName roleName) {
        this.userid = userid;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.city = city;
        this.status = status;
        this.roleName = roleName;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public Boolean getGender() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
    
}
