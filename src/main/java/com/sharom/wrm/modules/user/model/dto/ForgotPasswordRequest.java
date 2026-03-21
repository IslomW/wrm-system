package com.sharom.wrm.modules.user.model.dto;


import com.sharom.wrm.common.constant.MessageKey;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(
        @NotBlank(message = MessageKey.EMAIL_NOT_BLANK)
        @Email(message = MessageKey.EMAIL_INVALID)
        String email) {

}