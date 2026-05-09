package com.threektechone.resorthub.config.data;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@Order(3)
@RequiredArgsConstructor
public class RoleDataInit implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void run(String... args) {

        if (roleRepository.count() > 0)
            return;

        List<Role> roles = Arrays.stream(RoleName.values())
                .map(roleName -> Role.builder()
                        .roleName(roleName)
                        .build())
                .toList();

        roleRepository.saveAll(roles);

        System.out.println("✅ Seeded roles!");
    }
}
