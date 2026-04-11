package com.threektechone.resorthub.config.data;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.threektechone.resorthub.enums.AmenityName;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.repositories.ResortAmenityRepository;

import lombok.RequiredArgsConstructor;

@Component
@Order(2) // sau Role/User
@RequiredArgsConstructor
public class ResortAmenityDataInit implements CommandLineRunner {

    private final ResortAmenityRepository amenityRepository;
    
    @Transactional
    @Override
    public void run(String... args) {

        if (amenityRepository.count() > 0) return;

        List<ResortAmenity> amenities = Arrays.stream(AmenityName.values())
                .map(name -> ResortAmenity.builder()
                        .name(name)
                        .icon(getIcon(name))
                        .description(getDescription(name))
                        .build())
                .toList();

        amenityRepository.saveAll(amenities);

        System.out.println("✅ Seeded amenities!");
    }

    // =========================
    // HELPER
    // =========================

    private String getIcon(AmenityName name) {
        return switch (name) {
            case WIFI -> "wifi.png";
            case POOL -> "pool.png";
            case SPA -> "spa.png";
            case PARKING -> "parking.png";
            case RESTAURANT -> "restaurant.png";
            case GYM -> "gym.png";
            default -> "default.png";
        };
    }

    private String getDescription(AmenityName name) {
        return switch (name) {
            case WIFI -> "Free high-speed WiFi";
            case POOL -> "Outdoor swimming pool";
            case SPA -> "Relaxing spa services";
            case PARKING -> "Free parking area";
            case RESTAURANT -> "On-site restaurant";
            case GYM -> "Fitness center";
            default -> "Standard amenity";
        };
    }
}