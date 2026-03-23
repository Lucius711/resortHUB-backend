package com.threektechone.resorthub.dto.customer;

import java.time.LocalDate;

import com.threektechone.resorthub.enums.MealTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MealSelectionRequestDTO {
    private int menuId;
    private int quantity;
    private LocalDate date;
    private MealTime mealTime;
}
