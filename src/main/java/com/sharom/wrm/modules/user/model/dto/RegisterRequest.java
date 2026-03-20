package com.sharom.wrm.modules.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        String username,
        String password,
        String phoneNumber,
        @NotBlank(message = "Email bo'sh bo'lmasligi kerak")
        @Email(message = "Email noto'g'ri formatda")
        String email) {
}
