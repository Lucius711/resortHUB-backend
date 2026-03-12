package com.threektechone.resorthub.service.impl.CustomerModuleImpl;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.dto.CustomerModuleDTO.MealSelectionRequestDTO;
import com.threektechone.resorthub.mapper.BookingMealMapper;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.BookingMeal;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.repositories.ResortMenuRepository;
import com.threektechone.resorthub.service.CustomerModule.BookingMealService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingMealServiceImpl implements BookingMealService{

    private final ResortMenuRepository resortMenuRepository;

    private final BookingMealMapper bookingMealMapper;

    @Override
    public BookingMeal mapToBookingMeal(MealSelectionRequestDTO dto, Booking booking) {
        ResortMenu menu = resortMenuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

        BookingMeal meal = bookingMealMapper.toBookingMeal(dto);
        meal.setResortMenu(menu);
        meal.setBooking(booking);
        return meal;
    }

}
