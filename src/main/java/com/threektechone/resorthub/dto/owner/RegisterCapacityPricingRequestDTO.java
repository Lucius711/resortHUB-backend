package com.threektechone.resorthub.dto.owner;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterCapacityPricingRequestDTO {
    private int maxGuest;
    private BigDecimal price;
}
