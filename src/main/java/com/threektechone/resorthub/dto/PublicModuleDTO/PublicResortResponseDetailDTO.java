package com.threektechone.resorthub.dto.PublicModuleDTO;

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
public class PublicResortResponseDetailDTO {
    private int resortId;
    private String resortName;
    private String district;
    private String city;
    private String address;
    private ResortType type; 
    private String description;
    private int maxGuest;
    private BigDecimal averageRating;
    private List<Integer> amenityIds;
    private List<Integer> imageIds;
    private List<Integer> reviewIds;
    private BigDecimal price;
    private List<Integer> menuIds;
}
