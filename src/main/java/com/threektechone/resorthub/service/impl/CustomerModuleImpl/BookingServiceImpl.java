package com.threektechone.resorthub.service.impl.CustomerModuleImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingRequestDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.mapper.BookingMapper;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.BookingMeal;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.BookingRepository;
import com.threektechone.resorthub.repositories.ResortMenuRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.CustomerModule.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ResortRepository resortRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final ResortMenuRepository resortMenuRepository;

    @Override
    public void createBooking(BookingRequestDTO dto,String email,int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        User customer = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Booking booking = bookingMapper.toBooking(dto);
        booking.setCustomer(customer);
        booking.setResort(resort);
        booking.setStatus(BookingStatus.PENDING);

        List<BookingMeal> meals = dto.getMeals()
            .stream()
            .map(mealDto -> {

                BookingMeal meal = new BookingMeal();
                meal.setDate(mealDto.getDate());
                meal.setMealTime(mealDto.getMealTime());
                meal.setQuantity(mealDto.getQuantity());

                ResortMenu menu = resortMenuRepository.findById(mealDto.getMenuId())
                        .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

                meal.setResortMenu(menu);

                meal.setBooking(booking);

                return meal;
            })
            .toList();
        
        booking.setMeals(meals);
        bookingRepository.save(booking);
    }
    
}
