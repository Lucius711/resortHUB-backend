package com.threektechone.resorthub.service.impl.auth;

import java.time.LocalDateTime;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.DuplicateResourceException;
import com.threektechone.resorthub.common.exception.custom.InvalidOtpException;
import com.threektechone.resorthub.common.exception.custom.InvalidRefreshTokenException;
import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.config.properties.JwtProperties;
import com.threektechone.resorthub.config.security.UserDetails.UserDetailsServiceImpl;
import com.threektechone.resorthub.dto.auth.AuthRequestDTO;
import com.threektechone.resorthub.dto.auth.AuthResponseDTO;
import com.threektechone.resorthub.dto.auth.RefreshTokenRequestDTO;
import com.threektechone.resorthub.dto.auth.VerifyOTPRequestDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.mapper.otp.OTPMapper;
import com.threektechone.resorthub.models.OTP;
import com.threektechone.resorthub.models.RefreshToken;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.OTPRepository;
import com.threektechone.resorthub.repositories.RefreshTokenRepository;
import com.threektechone.resorthub.repositories.RoleRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.auth.AuthService;
import com.threektechone.resorthub.service.auth.JwtService;
import com.threektechone.resorthub.service.mail.MailService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final OTPRepository otpRepository;

    private final OTPMapper otpMapper;

    private final MailService mailService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsServiceImpl userDetailService;

    private final JwtProperties jwtProperties;
    

    //generate OTP 
    private String generateOtp() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
    
    //Verify OTP
    @Override
    public void verifyOTP(VerifyOTPRequestDTO request) {

         OTP otp = otpRepository
                .findByEmailAndOtpCode(request.getEmail(),request.getOtpCode())
                .orElseThrow(() -> new InvalidOtpException("Invalid OTP"));
        
        //Check otp code is expired
        if (otp.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new InvalidOtpException("OTP expired");
        }
        
        //Check email is existed
        if (userRepository.findByEmail(otp.getEmail()).isPresent()) {
            throw new DuplicateResourceException("User is existed!");
        }

        User user = otpMapper.toUser(otp);

        user.setPassword(otp.getPassword());

        Role role = roleRepository.findByRoleName(RoleName.CUSTOMER)
        .orElseThrow(() -> 
            new ResourceNotFoundException("Role not found!")
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
        throw new DuplicateResourceException("Email is existed!");
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
        User user = userRepository.findByEmail(authRequestDTO.getEmail())
        .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
            

        if (!passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword())) { 
            throw new BadCredentialsException("Invalid username or password");
        }

        if (user.getIsDeleted()) {
            throw new DisabledException("Account deleted");
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
        .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        if (refreshToken.isRevoked()) {
            throw new InvalidRefreshTokenException("Token revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidRefreshTokenException("Token expired");
        }

        String email = jwtService.extractEmail(token);

        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());

        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new InvalidRefreshTokenException("Refresh token expired");
        }

        String newAccessToken = jwtService.generateAccessToken(userDetails);

        return new AuthResponseDTO(newAccessToken, token,user.getRole().getRoleName().name());

    }


    @Override
    public void logout(RefreshTokenRequestDTO request) {
         String token = request.getRefreshToken();

    RefreshToken refreshToken = refreshTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

    if (refreshToken.isRevoked()) {
        throw new InvalidRefreshTokenException("Token already revoked");
    }

    refreshToken.setRevoked(true);
    refreshTokenRepository.save(refreshToken);
    }
    
}
