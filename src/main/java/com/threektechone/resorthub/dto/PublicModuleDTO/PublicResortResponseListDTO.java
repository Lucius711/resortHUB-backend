package com.threektechone.resorthub.dto.PublicModuleDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PublicResortResponseListDTO {
    private int resortId;
    private String resortName;
    private String district;
    private String city;
    private String description;
    private String thumbnail;
    private int maxGuest;
    private BigDecimal price;
    private BigDecimal averageRating;
}
