package com.threektechone.resorthub.config.data;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.threektechone.resorthub.enums.LostFoundItemStatus;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.LostFoundItem;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.BookingRepository;
import com.threektechone.resorthub.repositories.LostFoundItemRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@Order(11)
@RequiredArgsConstructor
public class LostFoundItemDataInit implements CommandLineRunner {

    private final LostFoundItemRepository lostFoundRepository;
    private final BookingRepository bookingRepository;

    private Faker faker;

    @PostConstruct
    public void init() {
        faker = new Faker();
    }
    
    @Transactional
    @Override
    public void run(String... args) {

        // tránh seed lại
        if (lostFoundRepository.count() > 0) return;

        List<Booking> bookings = bookingRepository.findAll();

        if (bookings.isEmpty()) return;

        for (int i = 0; i < 10; i++) {

            Booking booking = bookings.get(faker.random().nextInt(bookings.size()));

            // reporter: thường là customer của booking
            User reporter = booking.getCustomer();

            LostFoundItemStatus status =
                    LostFoundItemStatus.values()[faker.random().nextInt(LostFoundItemStatus.values().length)];

            LostFoundItem item = LostFoundItem.builder()
                    .booking(booking)
                    .reporter(reporter)
                    .description(faker.lorem().sentence())
                    .status(status)
                    .build();

            lostFoundRepository.save(item);
        }

        System.out.println("✅ Seeded 10 lost & found items!");
    }
}