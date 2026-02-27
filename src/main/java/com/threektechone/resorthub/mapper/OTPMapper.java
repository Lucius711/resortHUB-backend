package com.threektechone.resorthub.mapper;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.dto.AuthModuleDTO.AuthRequestDTO;
import com.threektechone.resorthub.models.OTP;
import com.threektechone.resorthub.models.User;

@Component
public class OTPMapper {

    public User toUser(OTP otp) {
        User user = new User();
        user.setEmail(otp.getEmail());
        user.setFullName(otp.getName());
        user.setPhone(otp.getPhone());
        user.setGender(otp.isGender());
        user.setDob(otp.getDob());
        user.setCity(otp.getCity());
        return user;
    }

    public OTP toOTP(AuthRequestDTO dto) {
        OTP otp = new OTP();
        otp.setName(dto.getName());
        otp.setEmail(dto.getEmail());
        otp.setPhone(dto.getPhone());
        otp.setGender(dto.isGender());
        otp.setDob(dto.getDob());
        otp.setCity(dto.getCity());
        otp.setVerified(false);
        return otp;
    }
    
}
