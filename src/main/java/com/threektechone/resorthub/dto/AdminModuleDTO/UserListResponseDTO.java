package com.threektechone.resorthub.dto.AdminModuleDTO;
import java.time.LocalDate;

import com.threektechone.resorthub.enums.RoleName;

public class UserListResponseDTO {
    
    private int userId;
    private String fullName;
    private String email;
    private Boolean gender;
    private LocalDate dob;
    private String phone;
    private String city;
    private RoleName roleName;

    public UserListResponseDTO(){}

    /**
     * Parameterized constructor for creating a UserListResponseDTO with user details.
     * 
     * @param fullName the full name of the user
     * @param email the email address of the user
     * @param gender the gender of the user
     * @param dob the date of birth of the user
     * @param phone the phone number of the user
     * @param city the city of the user
     * @param roleName the role name assigned to the user
     */
    public UserListResponseDTO(String fullName, String email, Boolean gender, LocalDate dob, String phone, String city, RoleName roleName) {
       this.fullName = fullName;
       this.email = email;
       this.gender = gender;
       this.dob = dob;
       this.phone = phone;
       this.city = city;
       this.roleName= roleName;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
    
}
