package com.threektechone.resorthub.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.threektechone.resorthub.dto.CustomerModuleDTO.MealSelectionRequestDTO;
import com.threektechone.resorthub.models.BookingMeal;

@Mapper(componentModel = "spring")
public interface BookingMealMapper {
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target= "date", source= "date")
    @Mapping(target = "quantity", source= "quantity")
    @Mapping(target = "mealTime", source = "mealTime")
    BookingMeal toBookingMeal(MealSelectionRequestDTO dto);

    @Mapping(target = "menuId", source = "resortMenu.menuId")
    MealSelectionRequestDTO toMealSelectionRequestDTO(BookingMeal meal);
    
}
