package com.threektechone.resorthub.dto.owner;

import com.threektechone.resorthub.enums.ResortType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterBasicInfoRequestDTO {
    private String resortName;
    private String description;
    private ResortType type;
    private String city; 
    private String district;
    private String address;
}
