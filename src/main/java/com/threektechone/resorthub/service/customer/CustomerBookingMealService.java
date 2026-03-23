package com.threektechone.resorthub.service.customer;

import com.threektechone.resorthub.dto.customer.MealSelectionRequestDTO;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.BookingMeal;

public interface  CustomerBookingMealService {
    BookingMeal mapToBookingMeal(MealSelectionRequestDTO dto, Booking booking);
}
