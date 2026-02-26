package com.threektechone.resorthub.service.impl.AuthModuleImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.dto.AuthModuleDTO.AuthRequestDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.AuthResponseDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.RoleRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.AuthModule.AuthService;
import com.threektechone.resorthub.service.AuthModule.JwtService;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    

    //Register new user
    @Override
    public void register(AuthRequestDTO authRequestDTO) {
       User user = new User();
       user.setFullName(authRequestDTO.getName());
       user.setEmail(authRequestDTO.getEmail());
       user.setPhone(authRequestDTO.getPhone());
       user.setGender(authRequestDTO.isGender());
       user.setDob(authRequestDTO.getDob());
       user.setCity(authRequestDTO.getCity());
       user.setPassword(passwordEncoder.encode(authRequestDTO.getPassword()));
       Role role = roleRepository.findByRoleName(RoleName.CUSTOMER)
       .orElseThrow(() -> new RuntimeException("Role not found: " + RoleName.CUSTOMER));
       
       user.setRole(role); // Set default role as CUSTOMER
       userRepository.save(user);
    }
    


    //Login user and generate JWT token
    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        // TODO Auto-generated method stub
        User user = userRepository.findByEmail(authRequestDTO.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found with email: " + authRequestDTO.getEmail()));
                


        if (!passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword())) { 
            throw new RuntimeException("Invalid password");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().getRoleName().name())
                .build();
        
        String accesstoken = jwtService.generateAccessToken(userDetails);
        String refreshtoken = jwtService.generateRefreshToken(userDetails);

        return new AuthResponseDTO(accesstoken, refreshtoken,user.getRole().getRoleName().name());
    }
    
}
