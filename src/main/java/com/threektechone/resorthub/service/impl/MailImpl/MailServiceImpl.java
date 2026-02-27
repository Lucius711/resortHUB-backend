package com.threektechone.resorthub.service.impl.MailImpl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.service.mail.MailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @Override
    public void sendOtpEmail(String toEmail, String otpCode) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("ResortHub - OTP Verification");
            
            String content = """
                    <h2>OTP Verification</h2>
                    <p>Your OTP code is:</p>
                    <h1 style="color:blue;">%s</h1>
                    <p>This code will expire in 5 minutes.</p>
                    <p>Do not share this code.</p>
                    """.formatted(otpCode);

            helper.setText(content, true);
            mailSender.send(mimeMessage);


        }
        catch(Exception e) {
            throw new RuntimeException("Failed to send email");
        }
    }
}
