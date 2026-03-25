package com.sharom.wrm.modules.user.model.dto;

import com.sharom.wrm.common.constant.MessageKey;
import com.sharom.wrm.common.util.ValidPassword;
import com.sharom.wrm.common.util.ValidPhone;
import com.sharom.wrm.service.MessageService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = MessageKey.USERNAME_NOT_BLANK)
        @Size(min = 4, max = 20, message = MessageKey.USERNAME_SIZE)
        String username,

        @NotBlank(message = MessageKey.PASSWORD_NOT_BLANK)
        @ValidPassword
        String password,

        @NotBlank(message = MessageKey.PHONE_NOT_BLANK)
        @ValidPhone
        String phoneNumber,

        @NotBlank(message = MessageKey.EMAIL_NOT_BLANK)
        @Email(message = MessageKey.EMAIL_INVALID)
        String email) {
}
