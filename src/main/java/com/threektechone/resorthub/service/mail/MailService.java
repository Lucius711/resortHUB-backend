package com.threektechone.resorthub.service.mail;

public interface MailService {
    void sendOtpEmail(String toEmail, String otpCode);
}
