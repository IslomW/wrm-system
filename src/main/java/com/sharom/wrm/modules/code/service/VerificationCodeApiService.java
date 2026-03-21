package com.sharom.wrm.modules.code.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeApiService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public Boolean sendCodeToEmail(String toEmail, String code) {

        try {
            String messageText = "Sizning tasdiqlash kodingiz: " + code +
                    "\nIltimos, kodni hech kimga bermang!";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Tasdiqlash kodi");
            message.setText(messageText);

            mailSender.send(message);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}