package com.threektechone.resorthub.dto.OwnerModuleDTO;

import java.math.BigDecimal;

import com.threektechone.resorthub.enums.MenuCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterMenuItemsDTO {
    private String name;
    private BigDecimal price;
    private MenuCategory category;
}
