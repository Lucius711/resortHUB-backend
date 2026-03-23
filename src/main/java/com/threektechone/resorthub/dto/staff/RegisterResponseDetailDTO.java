package com.threektechone.resorthub.dto.staff;

import java.math.BigDecimal;
import java.util.List;

import com.threektechone.resorthub.enums.ResortType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterResponseDetailDTO {
    private int resortId;
    private String resortCode;
    private String ownerName;
    private String resortName;
    private ResortType type;
    private String description;
    private String city; 
    private String district;
    private String address;
    private int maxGuest;
    private BigDecimal price;
    private List<Integer> amenityIds;
    private List<Integer> imageIds;
}
