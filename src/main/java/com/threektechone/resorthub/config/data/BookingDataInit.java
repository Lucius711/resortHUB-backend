package com.threektechone.resorthub.config.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.enums.MealTime;
import com.threektechone.resorthub.enums.PaymentStatus;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.BookingMeal;
import com.threektechone.resorthub.models.Payment;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.BookingMealRepository;
import com.threektechone.resorthub.repositories.BookingRepository;
import com.threektechone.resorthub.repositories.PaymentRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@Order(4)
@RequiredArgsConstructor
public class BookingDataInit implements CommandLineRunner {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final BookingMealRepository bookingMealRepository;
    private final UserRepository userRepository;
    private final ResortRepository resortRepository;

    private final Faker faker = new Faker();
    
    @Transactional
    @Override
    public void run(String... args) {

        // tránh seed lại
        if (bookingRepository.count() > 0) return;

        List<User> customers = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole().getRoleName() == RoleName.CUSTOMER)
                .toList();

        List<Resort> resorts = resortRepository.findAll();

        if (customers.isEmpty() || resorts.isEmpty()) return;

        for (int i = 1; i <= 10; i++) {

            User customer = customers.get(faker.random().nextInt(customers.size()));
            Resort resort = resorts.get(faker.random().nextInt(resorts.size()));

            LocalDateTime checkIn = LocalDateTime.now().plusDays(faker.number().numberBetween(1, 10));
            LocalDateTime checkOut = checkIn.plusDays(faker.number().numberBetween(1, 5));

            // =========================
            // BOOKING
            // =========================
            Booking booking = Booking.builder()
                    .bookingCode("BKG" + faker.number().digits(6))
                    .customer(customer)
                    .resort(resort)
                    .checkInDate(checkIn)
                    .checkOutDate(checkOut)
                    .numberOfPerson(faker.number().numberBetween(1, 5))
                    .status(BookingStatus.PENDING)
                    .acceptedTerms(true)
                    .totalPrice(BigDecimal.valueOf(faker.number().numberBetween(200, 1000)))
                    .build();

            bookingRepository.save(booking);

            // =========================
            // PAYMENT (1-1)
            // =========================
            Payment payment = Payment.builder()
                    .booking(booking)
                    .amount(booking.getTotalPrice())
                    .paymentMethod("CREDIT_CARD")
                    .paymentStatus(PaymentStatus.PENDING)
                    .build();

            paymentRepository.save(payment);

            // set lại cho booking (do OneToOne)
            booking.setPayment(payment);
            bookingRepository.save(booking);

            // =========================
            // MEAL
            // =========================
            if (resort.getMenuItems() != null && !resort.getMenuItems().isEmpty()) {

                ResortMenu menu = resort.getMenuItems()
                        .get(faker.random().nextInt(resort.getMenuItems().size()));

                BookingMeal meal = BookingMeal.builder()
                        .booking(booking)
                        .resortMenu(menu)
                        .date(LocalDate.now().plusDays(faker.number().numberBetween(1, 5)))
                        .mealTime(MealTime.values()[faker.random().nextInt(MealTime.values().length)])
                        .quantity(faker.number().numberBetween(1, 3))
                        .build();

                bookingMealRepository.save(meal);
            }
        }

        System.out.println("✅ Seeded 10 bookings successfully!");
    }
}