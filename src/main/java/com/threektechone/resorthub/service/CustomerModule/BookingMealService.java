package com.threektechone.resorthub.service.CustomerModule;

import com.threektechone.resorthub.dto.CustomerModuleDTO.MealSelectionRequestDTO;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.BookingMeal;

public interface  BookingMealService {
    BookingMeal mapToBookingMeal(MealSelectionRequestDTO dto, Booking booking);
}
