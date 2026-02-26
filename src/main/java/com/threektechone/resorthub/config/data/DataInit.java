package com.threektechone.resorthub.config.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.repositories.RoleRepository;

@Configuration
public class DataInit implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(RoleName.ADMIN));
            roleRepository.save(new Role(RoleName.CUSTOMER));
            roleRepository.save(new Role(RoleName.OWNER));
            roleRepository.save(new Role(RoleName.STAFF));

        }
    } 
}
