package com.sharom.wrm.modules.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTemplateService  {
    private final EmailService emailService;

    public void sendVerificationCode(String to, String code) {

        String subject = "Tasdiqlash kodi";

        String text = """
                Sizning tasdiqlash kodingiz: %s
                
                Kod 1 daqiqa amal qiladi.
                Uni hech kimga bermang!
                """.formatted(code);

        emailService.sendEmail(to, subject, text);
    }

    public void sendWelcomeEmail(String to, String username) {

        String subject = "Xush kelibsiz!";

        String text = """
                Assalomu alaykum, %s!
                
                Sizni bizning ilovamizdan ro‘yxatdan o‘tganingiz bilan tabriklaymiz 🎉
                
                Hurmat bilan,
                Bizning jamoa
                """.formatted(username);

        emailService.sendEmail(to, subject, text);
    }
}