package com.threektechone.resorthub.config.data;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortReview;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.ResortReviewRepository;
import com.threektechone.resorthub.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@Order(7) // sau Resort + User
@RequiredArgsConstructor
public class ResortReviewDataInit implements CommandLineRunner {

    private final ResortRepository resortRepository;
    private final UserRepository userRepository;
    private final ResortReviewRepository reviewRepository;

    private Faker faker;

    @PostConstruct
    public void init() {
        faker = new Faker();
    }

    @Transactional
    @Override
    public void run(String... args) {

        if (reviewRepository.count() > 0)
            return;

        List<Resort> resorts = resortRepository.findAll();

        List<User> customers = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole().getRoleName() == RoleName.CUSTOMER)
                .toList();

        if (resorts.isEmpty() || customers.isEmpty())
            return;

        for (Resort resort : resorts) {

            int reviewCount = faker.number().numberBetween(2, 6);

            for (int i = 0; i < reviewCount; i++) {

                User user = customers.get(faker.random().nextInt(customers.size()));

                ResortReview review = ResortReview.builder()
                        .resort(resort)
                        .user(user)
                        .rating(faker.number().numberBetween(3, 6)) // 3–5 sao
                        .comment(faker.lorem().sentence())
                        .build();

                reviewRepository.save(review);
            }
        }

        System.out.println("✅ Seeded reviews!");
    }
}