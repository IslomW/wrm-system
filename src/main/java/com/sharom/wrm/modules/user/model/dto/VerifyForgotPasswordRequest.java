package com.sharom.wrm.modules.user.model.dto;

public record VerifyForgotPasswordRequest(String email, String code) {
}
