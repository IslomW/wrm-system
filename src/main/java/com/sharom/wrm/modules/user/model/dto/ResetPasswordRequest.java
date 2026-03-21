package com.sharom.wrm.modules.user.model.dto;

public record ResetPasswordRequest(String email, String newPassword) {
}
