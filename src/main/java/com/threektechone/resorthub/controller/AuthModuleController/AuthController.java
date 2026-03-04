package com.threektechone.resorthub.controller.AuthModuleController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.dto.AuthModuleDTO.AuthRequestDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.AuthResponseDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.RefreshTokenRequestDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.VerifyOTPRequestDTO;
import com.threektechone.resorthub.service.AuthModule.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequestDTO authRequestDTO) {
        authService.register(authRequestDTO);
        return ResponseEntity.status(201).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO authResponseDTO = authService.login(authRequestDTO);
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOTPRequestDTO request) {
        authService.verifyOTP(request);
        return ResponseEntity.ok("Verify OTP successful. Account created!");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequestDTO request) {
        authService.logout(request);
        return ResponseEntity.ok("Logout successfully");
    }
    
    

}
