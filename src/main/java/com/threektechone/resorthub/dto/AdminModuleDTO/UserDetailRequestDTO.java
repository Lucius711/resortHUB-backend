package com.threektechone.resorthub.dto.AdminModuleDTO;

import java.time.LocalDate;

public class UserDetailRequestDTO {

    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    private String phone;
    private String city;
    
    public UserDetailRequestDTO(){}


    public UserDetailRequestDTO(String fullName, Boolean gender,
                                LocalDate dob, String phone, String city) {
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    
}
