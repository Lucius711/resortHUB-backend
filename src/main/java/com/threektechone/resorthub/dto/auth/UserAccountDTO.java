package com.threektechone.resorthub.dto.auth;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.models.Province;
import com.threektechone.resorthub.models.Ward;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAccountDTO {
    private int userId;
    private String userCode;
    private String fullName;
    private String email;
    private Boolean gender;
    private LocalDate dob;
    private String image;
    private String phone;
    private Province province;
    private Ward ward;
    private RoleName role;
    private UserStatus status;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
}
