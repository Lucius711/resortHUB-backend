package com.threektechone.resorthub.dto.auth;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterCacheDTO {
    private String email;
    private String name;
    private String phone;
    private Boolean gender;
    private LocalDate dob;
    private Integer provinceId;
    private Integer wardId;
    private String password;
    private String otpCode;
}
