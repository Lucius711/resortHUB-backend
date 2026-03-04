package com.threektechone.resorthub.dto.AuthModuleDTO;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthRequestDTO {

    private String name;
    private String email;
    private String phone;
    private String password;
    private Boolean gender;
    private LocalDate dob;
    private String city;
        
}
