package com.threektechone.resorthub.service.impl.customer;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.dto.customer.MealSelectionRequestDTO;
import com.threektechone.resorthub.mapper.booking.BookingMealMapper;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.BookingMeal;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.repositories.ResortMenuRepository;
import com.threektechone.resorthub.service.customer.CustomerBookingMealService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerBookingMealServiceImpl implements CustomerBookingMealService{

    private final ResortMenuRepository resortMenuRepository;

    private final BookingMealMapper bookingMealMapper;

    @Override
    public BookingMeal mapToBookingMeal(MealSelectionRequestDTO dto, Booking booking) {
        ResortMenu menu = resortMenuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

        // Prevent creating BookingMeal using menu from another resort.
        if (booking == null || booking.getResort() == null || booking.getResort().getResortId() != menu.getResort().getResortId()) {
            throw new ResourceNotFoundException("Menu not found for this resort");
        }

        BookingMeal meal = bookingMealMapper.toBookingMeal(dto);
        meal.setResortMenu(menu);
        meal.setBooking(booking);
        return meal;
    }

}
