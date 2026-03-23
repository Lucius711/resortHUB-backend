package com.threektechone.resorthub.service.common;

import java.math.BigDecimal;
import java.util.List;

import com.threektechone.resorthub.dto.customer.BookingRequestDTO;
import com.threektechone.resorthub.dto.customer.MealSelectionRequestDTO;
import com.threektechone.resorthub.models.Resort;

public interface BookingPriceCalculator {

    BigDecimal calculateTotalPrice(Resort resort, BookingRequestDTO dto);

    BigDecimal calculateMealCost(List<MealSelectionRequestDTO> meals);
}
