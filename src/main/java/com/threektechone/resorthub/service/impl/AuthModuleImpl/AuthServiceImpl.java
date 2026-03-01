package com.threektechone.resorthub.service.impl.AuthModuleImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.config.properties.JwtProperties;
import com.threektechone.resorthub.config.security.UserDetails.UserDetailsServiceImpl;
import com.threektechone.resorthub.dto.AuthModuleDTO.AuthRequestDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.AuthResponseDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.RefreshTokenRequestDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.VerifyOTPRequestDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.mapper.OTPMapper;
import com.threektechone.resorthub.models.OTP;
import com.threektechone.resorthub.models.RefreshToken;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.OTPRepository;
import com.threektechone.resorthub.repositories.RefreshTokenRepository;
import com.threektechone.resorthub.repositories.RoleRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.AuthModule.AuthService;
import com.threektechone.resorthub.service.AuthModule.JwtService;
import com.threektechone.resorthub.service.mail.MailService;


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


    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private OTPMapper otpMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailService;
    

    //generate OTP 
    private String generateOtp() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
    private final JwtProperties jwtProperties;

    public AuthServiceImpl(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    //Verify OTP
    @Override
    public void verifyOTP(VerifyOTPRequestDTO request) {

         OTP otp = otpRepository
                .findByEmailAndOtpCode(request.getEmail(),request.getOtpCode())
                .orElseThrow(() -> new RuntimeException("OTP invalid"));
        
        //Check otp code is expired
        if (otp.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }
        
        //Check email is existed
        if (userRepository.findByEmail(otp.getEmail()).isPresent()) {
            throw new RuntimeException("User is existed!");
        }

        User user = otpMapper.toUser(otp);

        user.setPassword(otp.getPassword());

        Role role = roleRepository.findByRoleName(RoleName.CUSTOMER)
        .orElseThrow(() -> 
            new RuntimeException("Role not found: " + RoleName.CUSTOMER)
        );

        user.setRole(role);

        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        otp.setVerified(true);
        otpRepository.save(otp);
    }
    

    //Register new user
    @Override
    public void register(AuthRequestDTO authRequestDTO) {

        //Check email is existed
        if (userRepository.findByEmail(authRequestDTO.getEmail()).isPresent()) {
        throw new RuntimeException("Email is existed!");
        }

        //Generate OTP Code
        String otpCode =generateOtp();

        OTP otp = otpMapper.toOTP(authRequestDTO);

        otp.setPassword(passwordEncoder.encode(authRequestDTO.getPassword()));

        otp.setOtpCode(otpCode);

        otp.setExpiredAt(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(otp);

        mailService.sendOtpEmail(authRequestDTO.getEmail(), otpCode);
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

        UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
        
        String accesstoken = jwtService.generateAccessToken(userDetails);
        String refreshtokenStr = jwtService.generateRefreshToken(userDetails);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshtokenStr);
        refreshToken.setUser(user);
        refreshToken.setRevoked(false);
        refreshToken.setExpiryDate(LocalDateTime.now()
            .plusSeconds(jwtProperties.getRefreshExpiration() / 1000));

        refreshTokenRepository.save(refreshToken);

        return new AuthResponseDTO(accesstoken, refreshtokenStr,user.getRole().getRoleName().name());
    }
    

    //Use RefreshToken to generate new access token
    @Override
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {
        String token = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
        .orElseThrow(() -> new RuntimeException("Token not found"));

        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Token revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        String email = jwtService.extractEmail(token);

        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());

        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtService.generateAccessToken(userDetails);

        return new AuthResponseDTO(newAccessToken, token,user.getRole().getRoleName().name());

    }


    @Override
    public void logout(RefreshTokenRequestDTO request) {
         String token = request.getRefreshToken();

    RefreshToken refreshToken = refreshTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new RuntimeException("Token not found"));

    if (refreshToken.isRevoked()) {
        throw new RuntimeException("Token already revoked");
    }

    refreshToken.setRevoked(true);
    refreshTokenRepository.save(refreshToken);
    }
    
}
