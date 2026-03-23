package com.threektechone.resorthub.controller.auth;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.auth.AuthRequestDTO;
import com.threektechone.resorthub.dto.auth.AuthResponseDTO;
import com.threektechone.resorthub.dto.auth.RefreshTokenRequestDTO;
import com.threektechone.resorthub.dto.auth.VerifyOTPRequestDTO;
import com.threektechone.resorthub.service.auth.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody AuthRequestDTO authRequestDTO) {
        authService.register(authRequestDTO);

        ApiResponse<String> response =new ApiResponse<>(201, null, "User registered successfully", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO authResponseDTO = authService.login(authRequestDTO);

        ApiResponse<AuthResponseDTO> response = new ApiResponse<>(200, null, authResponseDTO, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody VerifyOTPRequestDTO request) {
        authService.verifyOTP(request);

        ApiResponse<String> response =new ApiResponse<>(201, null, "Verify OTP successful. Account created!", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refreshToken(@RequestBody RefreshTokenRequestDTO request) {

        ApiResponse<AuthResponseDTO> response = new ApiResponse<>(200,null,authService.refreshToken(request),LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestBody RefreshTokenRequestDTO request) {
        authService.logout(request);
        
        ApiResponse<String> response =new ApiResponse<>(201, null, "Logout successfully!", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    

}
