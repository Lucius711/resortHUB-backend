package com.threektechone.resorthub.dto.admin;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailRequestDTO {
    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    private String phone;
    private int provinceId;
    private int wardId;

}
