package com.threektechone.resorthub.config.data;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.RoleRepository;
import com.threektechone.resorthub.repositories.UserRepository;

@Configuration
public class DataInit implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(RoleName.ADMIN));
            roleRepository.save(new Role(RoleName.CUSTOMER));
            roleRepository.save(new Role(RoleName.OWNER));
            roleRepository.save(new Role(RoleName.STAFF));
        }
         String pass = passwordEncoder.encode("123456");

String[][] data = {
        {"Nguyen Van A","a@gmail.com","Hanoi","ADMIN","ACTIVE","true"},
        {"Tran Thi B","b@gmail.com","HCM","CUSTOMER","ACTIVE","false"},
        {"Le Van C","c@gmail.com","Danang","STAFF","INACTIVE","true"},
        {"Pham Thi D","d@gmail.com","Hanoi","STAFF","ACTIVE","false"},
        {"Hoang Van E","e@gmail.com","HCM","CUSTOMER","BANNED","true"},
        {"Do Thi F","f@gmail.com","Hanoi","CUSTOMER","ACTIVE","false"},
        {"Bui Van G","g@gmail.com","Danang","STAFF","INACTIVE","true"},
        {"Dang Thi H","h@gmail.com","HCM","CUSTOMER","ACTIVE","false"},
        {"Nguyen Van I","i@gmail.com","Hanoi","CUSTOMER","BANNED","true"},
        {"Tran Van K","k@gmail.com","Danang","STAFF","ACTIVE","true"}
};

for (String[] d : data) {
    if (userRepository.findByEmail(d[1]).isEmpty()) {

        User u = new User();
        u.setFullName(d[0]);
        u.setEmail(d[1]);
        u.setPhone("09" + (int)(Math.random()*10000000));
        u.setCity(d[2]);
        u.setPassword(pass);
        u.setGender(Boolean.valueOf(d[5]));
        u.setDob(LocalDate.of(2000,1,1));
        u.setStatus(UserStatus.valueOf(d[4]));

        Role role = roleRepository.findByRoleName(RoleName.valueOf(d[3]))
                .orElseThrow();
        u.setRole(role);

        userRepository.save(u);
    }
}
    } 
}
