package com.threektechone.resorthub.config.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.models.EditResortRequest;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.EditResortRequestRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@Order(11)
@RequiredArgsConstructor
public class EditResortRequestDataInit implements CommandLineRunner {

    private final EditResortRequestRepository requestRepository;
    private final ResortRepository resortRepository;
    private final UserRepository userRepository;

    private Faker faker;

    @PostConstruct
    public void init() {
        faker = new Faker();
    }

    @Transactional
    @Override
    public void run(String... args) {

        // tránh seed lại
        if (requestRepository.count() > 0)
            return;

        List<Resort> resorts = resortRepository.findAll();

        List<User> owners = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole().getRoleName() == RoleName.OWNER)
                .toList();

        List<User> staffs = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole().getRoleName() == RoleName.STAFF)
                .toList();

        if (resorts.isEmpty() || owners.isEmpty() || staffs.isEmpty())
            return;

        for (int i = 0; i < 10; i++) {

            Resort resort = resorts.get(faker.random().nextInt(resorts.size()));
            User owner = resort.getOwner(); // 🔥 chuẩn business hơn
            User staff = staffs.get(faker.random().nextInt(staffs.size()));

            RequestStatus status = RequestStatus.values()[faker.random().nextInt(RequestStatus.values().length)];

            // fake JSON data (old vs new)
            String oldData = "{ \"name\": \"" + resort.getName() + "\", \"price\": " + resort.getPrice() + " }";

            String newData = "{ \"name\": \"" + faker.company().name() +
                    "\", \"price\": " + (resort.getPrice().intValue() + faker.number().numberBetween(100, 500)) +
                    " }";

            // logic thời gian
            LocalDateTime updatedAt = null;
            String note = null;

            if (status != RequestStatus.PENDING) {
                updatedAt = LocalDateTime.now().minusDays(faker.number().numberBetween(1, 10));
                note = faker.lorem().sentence();
            }

            EditResortRequest request = EditResortRequest.builder()
                    .resort(resort)
                    .oldData(oldData)
                    .newData(newData)
                    .requestStatus(status)
                    .createdBy(owner)
                    .approvedBy(staff)
                    .updatedAt(updatedAt)
                    .note(note)
                    .build();

            requestRepository.save(request);
        }

        System.out.println("✅ Seeded 10 edit resort requests!");
    }
}