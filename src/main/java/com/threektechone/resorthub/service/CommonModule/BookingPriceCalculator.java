package com.threektechone.resorthub.service.CommonModule;

import java.math.BigDecimal;
import java.util.List;

import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingRequestDTO;
import com.threektechone.resorthub.dto.CustomerModuleDTO.MealSelectionRequestDTO;
import com.threektechone.resorthub.models.Resort;

public interface BookingPriceCalculator {

    BigDecimal calculateTotalPrice(Resort resort, BookingRequestDTO dto);

    BigDecimal calculateMealCost(List<MealSelectionRequestDTO> meals);
}
