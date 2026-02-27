package com.threektechone.resorthub.service.AuthModule;
import com.threektechone.resorthub.dto.AuthModuleDTO.AuthRequestDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.AuthResponseDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.RefreshTokenRequestDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.VerifyOTPRequestDTO;

public interface AuthService {
    
    void register(AuthRequestDTO authRequestDTO);

    void verifyOTP(VerifyOTPRequestDTO verifyOTPRequestDTO);

    AuthResponseDTO login(AuthRequestDTO authRequestDTO);

    AuthResponseDTO refreshToken(RefreshTokenRequestDTO request);

    void logout(RefreshTokenRequestDTO request);
    
}
