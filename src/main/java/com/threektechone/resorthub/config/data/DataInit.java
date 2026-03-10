package com.threektechone.resorthub.config.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.threektechone.resorthub.enums.AmenityName;
import com.threektechone.resorthub.enums.ResortRegistrationStep;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.models.ResortImage;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.ResortAmenityRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.RoleRepository;
import com.threektechone.resorthub.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ResortRepository resortRepository;
    private final ResortAmenityRepository resortAmenityRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        // Initialize roles
        if (roleRepository.count() == 0) {
            Role role1 = new Role();
            role1.setRoleName(RoleName.ADMIN);
            roleRepository.save(role1);

            Role role2 = new Role();
            role2.setRoleName(RoleName.CUSTOMER);
            roleRepository.save(role2);

            Role role3 = new Role();
            role3.setRoleName(RoleName.OWNER);
            roleRepository.save(role3);

            Role role4 = new Role();
            role4.setRoleName(RoleName.STAFF);
            roleRepository.save(role4);
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
      User staff = userRepository.findById(2).orElseThrow();
// 2. Tạo các Amenity (Lưu riêng lẻ trước)
    ResortAmenity wifi = ResortAmenity.builder().name(AmenityName.WIFI).build();
    ResortAmenity pool = ResortAmenity.builder().name(AmenityName.POOL).build();
    resortAmenityRepository.saveAll(List.of(wifi, pool));

    // 3. Khởi tạo Resort 1
    Resort r1 = Resort.builder()
            .resortCode("RS201")
            .name("Azure Sky Resort")
            .city("Da Nang")
            .price(new BigDecimal("1500000"))
            .status(ResortStatus.ACTIVE)
            .step(ResortRegistrationStep.COMPLETED)
            .maxGuest(4)
            .averageRating(new BigDecimal("4.5"))
            .owner(owner)
            .staff(staff)
            .amenities(Set.of(wifi, pool)) // Gán Many-to-Many
            .build();

    // 4. Tạo List Images cho Resort 1 (Gán thủ công để đảm bảo resort_id không null)
    ResortImage img1 = ResortImage.builder().imageUrl("https://example.com/r1_1.jpg").resort(r1).build();
    ResortImage img2 = ResortImage.builder().imageUrl("https://example.com/r1_2.jpg").resort(r1).build();
    r1.setImages(List.of(img1, img2));

    // 5. Lưu Resort 1 (Lưu cái này sẽ tự lưu luôn Images nếu bạn có CascadeType.ALL)
    resortRepository.save(r1);


        Resort r2 = new Resort();
        r2.setResortCode("RS202");
        r2.setName("Palm Tree Resort");
        r2.setCity("Da Nang");
        r2.setPrice(BigDecimal.valueOf(1500000));
        r2.setStep(ResortRegistrationStep.COMPLETED);
        r2.setMaxGuest(4);
        r2.setAverageRating(BigDecimal.valueOf(4.5));
        r2.setCreatedAt(LocalDateTime.now());
        r2.setStatus(ResortStatus.REJECTED);
        r2.setOwner(owner);
        resortRepository.save(r2);

    } 
}
