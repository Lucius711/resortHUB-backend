package com.threektechone.resorthub.config.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.threektechone.resorthub.enums.ContractStatus;
import com.threektechone.resorthub.enums.ContractType;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.models.Contract;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.ContractRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.RoleRepository;
import com.threektechone.resorthub.repositories.UserRepository;

@Configuration
public class DataInit implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResortRepository resortRepository;

    @Autowired
    private ContractRepository contractRepository;

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
        {"Dang Thi H","h@gmail.com","HCM","OWNER","ACTIVE","false"},
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
    User owner = userRepository.findById(8)
                .orElseThrow();
        Resort r1 = new Resort();
        r1.setResortCode("RS201");
        r1.setName("Azure Sky Resort");
        r1.setLocation("Da Nang");
        r1.setPrice(BigDecimal.valueOf(1500000));
        r1.setStatus(ResortStatus.APPROVED);
        r1.setMaxGuest(4);
        r1.setAverageRating(BigDecimal.valueOf(4.5));
        r1.setCreatedAt(LocalDateTime.now());
        r1.setOwner(owner);
        resortRepository.save(r1);



        Resort r2 = new Resort();
        r2.setResortCode("RS202");
        r2.setName("Palm Tree Resort");
        r2.setLocation("Da Nang");
        r2.setPrice(BigDecimal.valueOf(1500000));
        r2.setStatus(ResortStatus.APPROVED);
        r2.setMaxGuest(4);
        r2.setAverageRating(BigDecimal.valueOf(4.5));
        r2.setCreatedAt(LocalDateTime.now());
        r2.setStatus(ResortStatus.REJECTED);
        r2.setOwner(owner);
        resortRepository.save(r2);

        User staff = userRepository.findById(2).orElseThrow();

        Resort resort1 = resortRepository.findById(1).orElseThrow();
        Resort resort2 = resortRepository.findById(2).orElseThrow();

        Contract c1 = new Contract();
        c1.setResort(resort1);
        c1.setOwner(owner);
        c1.setStaff(staff);
        c1.setStatus(ContractStatus.ACTIVE);
        c1.setContractType(ContractType.PARTNERSHIP);
        c1.setStartDate((LocalDate.now()));
        c1.setEndDate((LocalDate.of(2030, 12, 5)));
        contractRepository.save(c1);


        Contract c2 = new Contract();
        c2.setResort(resort2);
        c2.setOwner(owner);
        c2.setStaff(staff);
        c2.setStatus(ContractStatus.EXPIRED);
        c2.setContractType(ContractType.EXCLUSIVE);
        c2.setStartDate(((LocalDate.of(2000, 05, 12))));
        c2.setEndDate((LocalDate.now()));
        contractRepository.save(c2);

        

    
    } 
}
