package com.sharom.wrm.modules.code.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {
    public String getCode() {
        Random random = new Random();
        int randomCode = 10000 + random.nextInt(90000); // Generates a number between 10000 and 99999
        return String.valueOf(randomCode);
    }
}
