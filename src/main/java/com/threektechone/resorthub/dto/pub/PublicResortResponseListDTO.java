package com.threektechone.resorthub.dto.pub;

import java.math.BigDecimal;

import com.threektechone.resorthub.enums.ResortType;

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
    private String thumbnail;
    private ResortType type;
    private int maxGuest;
    private BigDecimal price;
    private BigDecimal averageRating;
}
