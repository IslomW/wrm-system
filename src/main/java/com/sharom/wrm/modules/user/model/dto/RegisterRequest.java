package com.sharom.wrm.modules.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Username bo'sh bo'lmasligi kerak")
        @Size(min = 3, message = "Username minimal 4ta bolishi kerak")
        String username,
        @NotBlank(message = "Password bo'sh bo'lmasligi kerak")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_&]).{8,}$",
                message = "Password kamida 8 ta, 1 katta, 1 kichik, 1 son va 1 maxsus belgi bo‘lishi kerak"
        )
        String password,
        @NotBlank(message = "Phone number bo'sh bo'lmasligi kerak")
        @Pattern(regexp = "^\\+998\\d{9}$", message = "Telefon formati noto'g'ri (+998901234567)")
        String phoneNumber,
        @NotBlank(message = "Email bo'sh bo'lmasligi kerak")
        @Email(message = "Email noto'g'ri formatda")
        String email) {
}
