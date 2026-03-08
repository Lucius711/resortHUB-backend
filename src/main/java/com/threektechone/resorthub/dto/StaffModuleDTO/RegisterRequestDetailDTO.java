package com.threektechone.resorthub.dto.StaffModuleDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterRequestDetailDTO {
    private int resortId;
    private String resortCode;
    private String ownerName;
    private String resortName;
    private String description;
    private String city; 
    private String district;
    private String address;
    private int maxGuest;
    private BigDecimal price;
}
