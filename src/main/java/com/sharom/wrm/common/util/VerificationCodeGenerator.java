package com.sharom.wrm.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class VerificationCodeGenerator {
    public String getCode() {
        Random random = new Random();
        int randomCode = 10000 + random.nextInt(90000); // Generates a number between 10000 and 99999
        return String.valueOf(randomCode);
    }
}
