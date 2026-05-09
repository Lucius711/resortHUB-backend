package com.threektechone.resorthub.config.data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.threektechone.resorthub.enums.MenuCategory;
import com.threektechone.resorthub.enums.ResortRegistrationStep;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.enums.ResortType;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.models.ResortImage;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.ResortAmenityRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@Order(4)
@RequiredArgsConstructor
public class ResortDataInit implements CommandLineRunner {

    private final ResortRepository resortRepository;
    private final UserRepository userRepository;
    private final ResortAmenityRepository amenityRepository;

    private Faker faker;

    @PostConstruct
    public void init() {
        faker = new Faker();
    }
    
    @Transactional
    @Override
    public void run(String... args) {

        if (resortRepository.count() > 0) return;

        List<User> owners = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole().getRoleName() == RoleName.OWNER)
                .toList();

        List<User> staffs = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole().getRoleName() == RoleName.STAFF)
                .toList();

        List<ResortAmenity> amenities = amenityRepository.findAll();

        if (owners.isEmpty() || amenities.isEmpty()) return;

        for (int i = 0; i < 10; i++) {

            User owner = owners.get(faker.random().nextInt(owners.size()));
            User staff = staffs.isEmpty() ? null : staffs.get(faker.random().nextInt(staffs.size()));
            Random random = new Random();

            Set<ResortAmenity> randomAmenities = random.ints(0, amenities.size())
            .distinct()
            .limit(2 + random.nextInt(2))
            .mapToObj(amenities::get)
            .collect(Collectors.toSet());

            Resort resort = Resort.builder()
                    .resortCode("R-" + UUID.randomUUID().toString().substring(0, 6))
                    .owner(owner)
                    .staff(staff)
                    .name(faker.company().name() + " Resort")
                    .description(faker.lorem().sentence())
                    .type(faker.options().option(ResortType.values()))
                    .city("Hanoi")
                    .district(faker.address().city())
                    .address(faker.address().fullAddress())
                    .maxGuest(faker.number().numberBetween(2, 10))
                    .averageRating(BigDecimal.valueOf(faker.number().randomDouble(1, 4, 5)))
                    .price(BigDecimal.valueOf(faker.number().numberBetween(500000, 3000000)))
                    .status(ResortStatus.ACTIVE)
                    .step(ResortRegistrationStep.COMPLETED)
                    .completedSteps(5)
                    .totalSteps(5)
                    .amenities(randomAmenities)
                    .build();

            // 🔥 Images
            List<ResortImage> images = List.of(
                    ResortImage.builder()
                            .imageUrl("https://source.unsplash.com/400x300/?resort&sig=" + faker.number().digits(3))
                            .resort(resort)
                            .build(),
                    ResortImage.builder()
                            .imageUrl("https://source.unsplash.com/400x300/?hotel&sig=" + faker.number().digits(3))
                            .resort(resort)
                            .build()
            );

            // 🔥 Menu
        List<ResortMenu> menus = List.of(
            createMenu(resort, MenuCategory.MAIN),
            createMenu(resort, MenuCategory.DRINK),
            createMenu(resort, MenuCategory.DESSERT),
            createMenu(resort, MenuCategory.APPETIZER)
        );


            resort.setImages(images);
            resort.setMenuItems(menus);

            resortRepository.save(resort);
        }

        System.out.println("✅ Seeded 10 resorts!");
    }
    private ResortMenu createMenu(Resort resort, MenuCategory category) {

    String name;
    BigDecimal price;

    switch (category) {
        case MAIN -> {
            name = faker.food().dish();
            price = BigDecimal.valueOf(faker.number().numberBetween(100000, 300000));
        }
        case DRINK -> {
            name = faker.beer().name();
            price = BigDecimal.valueOf(faker.number().numberBetween(20000, 80000));
        }
        case DESSERT -> {
            name = faker.food().ingredient() + " Cake";
            price = BigDecimal.valueOf(faker.number().numberBetween(30000, 100000));
        }
        case APPETIZER -> {
            name = faker.food().spice() + " Snack";
            price = BigDecimal.valueOf(faker.number().numberBetween(50000, 150000));
        }
        default -> {
            name = "Menu";
            price = BigDecimal.valueOf(50000);
        }
    }

    return ResortMenu.builder()
            .name(name)
            .price(price)
            .category(category) 
            .resort(resort)
            .build();
}
}