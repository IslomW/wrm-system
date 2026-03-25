package com.sharom.wrm.modules.user.service;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

}
