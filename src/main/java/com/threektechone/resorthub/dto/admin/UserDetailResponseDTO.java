package com.threektechone.resorthub.dto.admin;

import java.time.LocalDate;

import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailResponseDTO {
    private int userId;
    private String userCode;
    private String fullName;
    private String email;
    private Boolean gender;
    private LocalDate dob;
    private String phone;
    private int provinceId;
    private int wardId;
    private UserStatus status;
    private RoleName roleName;

}
