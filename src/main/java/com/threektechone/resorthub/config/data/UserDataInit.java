package com.threektechone.resorthub.config.data;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.RoleRepository;
import com.threektechone.resorthub.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@Order(2)
@RequiredArgsConstructor
public class UserDataInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private Faker faker;

    @PostConstruct
    public void init() {
        faker = new Faker();
    }
    
    @Transactional
    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) return;

        Role adminRole = roleRepository.findByRoleName(RoleName.ADMIN).orElseThrow();
        Role ownerRole = roleRepository.findByRoleName(RoleName.OWNER).orElseThrow();
        Role staffRole = roleRepository.findByRoleName(RoleName.STAFF).orElseThrow();
        Role customerRole = roleRepository.findByRoleName(RoleName.CUSTOMER).orElseThrow();

        String defaultPassword = passwordEncoder.encode("123456");

        // 🔥 1. ADMIN (fixed)
        User admin = User.builder()
                .fullName("Admin")
                .email("admin@gmail.com")
                .gender(true)
                .dob(LocalDate.of(2000, 1, 1))
                .password(defaultPassword)
                .phone("0900000000")
                .city("Hanoi")
                .status(UserStatus.ACTIVE)
                .role(adminRole)
                .build();

        userRepository.save(admin);

        // 🔥 2. OWNER
        createUsers(ownerRole, 5, defaultPassword);

        // 🔥 3. STAFF
        createUsers(staffRole, 5, defaultPassword);

        // 🔥 4. CUSTOMER
        createUsers(customerRole, 10, defaultPassword);

        System.out.println("✅ Seeded users!");
    }

    // =========================
    // HELPER
    // =========================

    private void createUsers(Role role, int count, String password) {

        for (int i = 0; i < count; i++) {

            String email = faker.internet().emailAddress();
            String phone = "0" + faker.number().digits(9);

            // tránh duplicate
            if (userRepository.existsByEmail(email)) continue;
            if (userRepository.existsByPhone(phone)) continue;

            User user = User.builder()
                    .fullName(faker.name().fullName())
                    .email(email)
                    .gender(faker.bool().bool())
                    .dob(LocalDate.of(
                            faker.number().numberBetween(1980, 2005),
                            faker.number().numberBetween(1, 12),
                            faker.number().numberBetween(1, 28)
                    ))
                    .image("https://i.pravatar.cc/150?u=" + UUID.randomUUID())
                    .password(password)
                    .phone(phone)
                    .city(faker.address().city())
                    .status(UserStatus.ACTIVE)
                    .role(role)
                    .build();

            userRepository.save(user);
        }
    }
}