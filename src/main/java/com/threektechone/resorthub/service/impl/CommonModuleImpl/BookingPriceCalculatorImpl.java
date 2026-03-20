package com.threektechone.resorthub.service.impl.CommonModuleImpl;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingRequestDTO;
import com.threektechone.resorthub.dto.CustomerModuleDTO.MealSelectionRequestDTO;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.repositories.ResortMenuRepository;
import com.threektechone.resorthub.service.CommonModule.BookingPriceCalculator;

import lombok.RequiredArgsConstructor;

import com.threektechone.resorthub.common.exception.custom.InvalidBookingStatusException;

@Service
@RequiredArgsConstructor
public class BookingPriceCalculatorImpl implements BookingPriceCalculator {

    private final ResortMenuRepository resortMenuRepository;
    
    //calculate total price of booking
    @Override
    public BigDecimal calculateTotalPrice(Resort resort, BookingRequestDTO dto) {
        long nights = ChronoUnit.DAYS.between(
                dto.getCheckInDate().toLocalDate(),
                dto.getCheckOutDate().toLocalDate()
        );

        if (nights < 1) {
            throw new InvalidBookingStatusException("Booking duration must be at least 1 night");
        }

        BigDecimal resortCost = resort.getPrice().multiply(BigDecimal.valueOf(nights));

        BigDecimal mealCost = calculateMealCost(dto.getMeals());
 
        return resortCost.add(mealCost);
    }
    

    //calculate meal cost of booking
    @Override
    public BigDecimal calculateMealCost(List<MealSelectionRequestDTO> meals) {
        BigDecimal mealCost = BigDecimal.ZERO;

        for (MealSelectionRequestDTO meal : meals) {
            ResortMenu menu = resortMenuRepository.findById(meal.getMenuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

            BigDecimal price = menu.getPrice()
                    .multiply(BigDecimal.valueOf(meal.getQuantity()));

            mealCost = mealCost.add(price);
        }

        return mealCost;
    }
    
}
