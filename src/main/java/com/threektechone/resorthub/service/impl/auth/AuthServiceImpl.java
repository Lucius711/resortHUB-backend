package com.threektechone.resorthub.service.impl.auth;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisTemplate;
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
import com.threektechone.resorthub.dto.auth.RegisterCacheDTO;
import com.threektechone.resorthub.dto.auth.UserAccountDTO;
import com.threektechone.resorthub.dto.auth.VerifyOTPRequestDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.mapper.otp.RegisterCacheMapper;
import com.threektechone.resorthub.models.OTP;
import com.threektechone.resorthub.models.Province;
import com.threektechone.resorthub.models.RefreshToken;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.models.Ward;
import com.threektechone.resorthub.repositories.OTPRepository;
import com.threektechone.resorthub.repositories.ProvinceRepository;
import com.threektechone.resorthub.repositories.RefreshTokenRepository;
import com.threektechone.resorthub.repositories.RoleRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.repositories.WardRepository;
import com.threektechone.resorthub.service.auth.AuthService;
import com.threektechone.resorthub.service.auth.JwtBlacklistService;
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

    private final ProvinceRepository provinceRepository;

    private final WardRepository wardRepository;

    private final OTPRepository otpRepository;

    private final MailService mailService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsServiceImpl userDetailService;

    private final JwtProperties jwtProperties;

    private final RegisterCacheMapper registerCacheMapper;

    private final JwtBlacklistService jwtBlacklistService;

    private final RedisTemplate<String, Object> redisTemplate;

    // generate OTP
    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    // Verify OTP
    @Override
    public void verifyOTP(VerifyOTPRequestDTO request) {

        String key = "register:otp:" + request.getEmail();

        RegisterCacheDTO cache = (RegisterCacheDTO) redisTemplate.opsForValue().get(key);

        if (cache == null) {
            throw new InvalidOtpException("OTP expired or not found");
        }

        if (!cache.getOtpCode().equals(request.getOtpCode())) {
            throw new InvalidOtpException("Invalid OTP");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User already exists");
        }

        User user = registerCacheMapper.toUser(cache);

        Province province = provinceRepository.findById(cache.getProvinceId())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found"));

        Ward ward = wardRepository.findById(cache.getWardId())
                .orElseThrow(() -> new ResourceNotFoundException("Ward not found"));

        user.setProvince(province);
        user.setWard(ward);

        Role role = roleRepository.findByRoleName(RoleName.CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        redisTemplate.delete(key);
    }

    @Override
    public void resendOTP(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateResourceException("User already exists");
        }

        OTP existingOtp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("OTP not found"));

        String newOtp = generateOtp();

        existingOtp.setOtpCode(newOtp);
        existingOtp.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        existingOtp.setVerified(false);

        otpRepository.save(existingOtp);

        mailService.sendOtpEmail(email, newOtp);
    }

    // Register new user
    @Override
    public void register(AuthRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists!");
        }

        String otpCode = generateOtp();

        RegisterCacheDTO cache = new RegisterCacheDTO();
        cache.setEmail(dto.getEmail());
        cache.setName(dto.getName());
        cache.setPhone(dto.getPhone());
        cache.setGender(dto.getGender());
        cache.setDob(dto.getDob());

        // ✅ replace city → master data IDs
        cache.setProvinceId(dto.getProvinceId());
        cache.setWardId(dto.getWardId());

        cache.setPassword(passwordEncoder.encode(dto.getPassword()));
        cache.setOtpCode(otpCode);

        String key = "register:otp:" + dto.getEmail();

        redisTemplate.opsForValue().set(key, cache, Duration.ofMinutes(5));

        mailService.sendOtpEmail(dto.getEmail(), otpCode);
    }

    // Login user and generate JWT token
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

        return new AuthResponseDTO(accesstoken, refreshtokenStr, user.getRole().getRoleName().name());
    }

    // Use RefreshToken to generate new access token
    @Override
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {
        String token = request.getRefreshToken();

        String refreshJti = jwtService.extractJti(token);
        if (jwtBlacklistService.isBlacklisted(refreshJti)) {
            throw new InvalidRefreshTokenException("Token revoked");
        }

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

        return new AuthResponseDTO(newAccessToken, token, user.getRole().getRoleName().name());

    }

    @Override
    public void logout(RefreshTokenRequestDTO request) {
        String token = request.getRefreshToken();

        String refreshJti = jwtService.extractJti(token);
        jwtBlacklistService.blacklistJti(
                refreshJti,
                jwtService.extractExpiration(token).toInstant());

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        if (refreshToken.isRevoked()) {
            throw new InvalidRefreshTokenException("Token already revoked");
        }

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public UserAccountDTO getMe(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new UserAccountDTO(
                user.getUserId(),
                user.getUserCode(),
                user.getFullName(),
                user.getEmail(),
                user.getGender(),
                user.getDob(),
                user.getImage(),
                user.getPhone(),
                user.getProvince(),
                user.getWard(),
                user.getRole().getRoleName(),
                user.getStatus(),
                user.getIsDeleted(),
                user.getCreatedAt());
    }

}
