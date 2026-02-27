package com.threektechone.resorthub.dto.AuthModuleDTO;

public class VerifyOTPRequestDTO {
    private String email;
    private String otpCode;

    public VerifyOTPRequestDTO() {}

    public VerifyOTPRequestDTO(String email,String otpCode) {
        this.email=email;
        this.otpCode=otpCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode=otpCode;
    }
}
