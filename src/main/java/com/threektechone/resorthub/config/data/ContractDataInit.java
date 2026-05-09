package com.threektechone.resorthub.config.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.threektechone.resorthub.enums.ContractStatus;
import com.threektechone.resorthub.enums.ContractType;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.models.Contract;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.ContractRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@Order(10)
@RequiredArgsConstructor
public class ContractDataInit implements CommandLineRunner {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final ResortRepository resortRepository;

    private Faker faker;

    @PostConstruct
    public void init() {
        faker = new Faker();
    }
    
    @Transactional
    @Override
    public void run(String... args) {

        // tránh seed lại
        if (contractRepository.count() > 0) return;

        List<User> owners = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole().getRoleName() == RoleName.OWNER)
                .toList();

        List<User> staffs = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole().getRoleName() == RoleName.STAFF)
                .toList();

        List<Resort> resorts = resortRepository.findAll();

        if (owners.isEmpty() || staffs.isEmpty() || resorts.isEmpty()) return;

        for (int i = 0; i < 10; i++) {

            User owner = owners.get(faker.random().nextInt(owners.size()));
            User staff = staffs.get(faker.random().nextInt(staffs.size()));
            Resort resort = resorts.get(faker.random().nextInt(resorts.size()));

            // random type
            ContractType type = ContractType.values()[faker.random().nextInt(ContractType.values().length)];

            // random status
            ContractStatus status = ContractStatus.values()[faker.random().nextInt(ContractStatus.values().length)];

            // logic signed
            boolean signed = status == ContractStatus.ACTIVE;

            LocalDateTime signedAt = signed
                    ? LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30))
                    : null;

            Contract contract = Contract.builder()
                    .owner(owner)
                    .staff(staff)
                    .resort(resort)
                    .contractType(type)
                    .fileUrl("https://example.com/contracts/" + faker.number().digits(5) + ".pdf")
                    .status(status)
                    .signedByOwner(signed)
                    .acceptedTerms(signed)
                    .signedAt(signedAt)
                    .build();

            contractRepository.save(contract);
        }

        System.out.println("✅ Seeded 10 contracts!");
    }
}