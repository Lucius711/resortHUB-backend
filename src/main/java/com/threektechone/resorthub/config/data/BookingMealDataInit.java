package com.threektechone.resorthub.config.data;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.threektechone.resorthub.enums.MealTime;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.BookingMeal;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.repositories.BookingMealRepository;
import com.threektechone.resorthub.repositories.BookingRepository;

import lombok.RequiredArgsConstructor;

@Component
@Order(8)
@RequiredArgsConstructor
public class BookingMealDataInit implements CommandLineRunner {

    private final BookingMealRepository bookingMealRepository;
    private final BookingRepository bookingRepository;
    
    @Transactional
    @Override
    public void run(String... args) {

        // tránh seed lại
        if (bookingMealRepository.count() > 0) return;

        List<Booking> bookings = bookingRepository.findAll();

        if (bookings.isEmpty()) return;

        for (int i = 0; i < 10; i++) {

            Booking booking = bookings.get(i % bookings.size());
            Resort resort = booking.getResort();

            // ❗ check menu null tránh crash
            if (resort.getMenuItems() == null || resort.getMenuItems().isEmpty()) {
                continue;
            }

            List<ResortMenu> menus = resort.getMenuItems();

            // random đơn giản
            ResortMenu menu = menus.get(i % menus.size());
            MealTime mealTime = MealTime.values()[i % MealTime.values().length];

            BookingMeal meal = BookingMeal.builder()
                    .booking(booking)
                    .resortMenu(menu)
                    .date(LocalDate.now().plusDays(i + 1))
                    .mealTime(mealTime)
                    .quantity(1 + (i % 3))
                    .build();

            bookingMealRepository.save(meal);
        }

        System.out.println("✅ Seeded 10 booking meals!");
    }
}