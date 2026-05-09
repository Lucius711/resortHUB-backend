package com.threektechone.resorthub.service.auth;

import com.threektechone.resorthub.dto.auth.AuthRequestDTO;
import com.threektechone.resorthub.dto.auth.AuthResponseDTO;
import com.threektechone.resorthub.dto.auth.RefreshTokenRequestDTO;
import com.threektechone.resorthub.dto.auth.UserAccountDTO;
import com.threektechone.resorthub.dto.auth.VerifyOTPRequestDTO;

public interface AuthService {

    void register(AuthRequestDTO authRequestDTO);

    void verifyOTP(VerifyOTPRequestDTO verifyOTPRequestDTO);

    void resendOTP(String email);

    AuthResponseDTO login(AuthRequestDTO authRequestDTO);

    AuthResponseDTO refreshToken(RefreshTokenRequestDTO request);

    void logout(RefreshTokenRequestDTO request);

    UserAccountDTO getMe(String email);

}
