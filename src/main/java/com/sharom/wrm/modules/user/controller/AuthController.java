package com.sharom.wrm.modules.user.controller;

import com.sharom.wrm.common.constant.MessageKey;
import com.sharom.wrm.common.response.ApiResponse;
import com.sharom.wrm.common.response.ResponseFactory;
import com.sharom.wrm.modules.user.model.dto.AuthResponse;
import com.sharom.wrm.modules.user.model.dto.ForgotPasswordRequest;
import com.sharom.wrm.modules.user.model.dto.VerifyForgotPasswordRequest;
import com.sharom.wrm.modules.user.model.dto.ResetPasswordRequest;
import com.sharom.wrm.modules.user.model.dto.LoginRequest;
import com.sharom.wrm.modules.user.model.dto.RegisterRequest;
import com.sharom.wrm.modules.user.service.UserService;
import com.sharom.wrm.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService authService;
    private final MessageService messageService;

    /* ================= REGISTER ================= */

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        AuthResponse response = authService.register(request);
        return ResponseFactory.created(response);
    }

    /* ================= LOGIN ================= */

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody LoginRequest request
    ) {
        AuthResponse response =
                authService.login(request.username(), request.password());
        return ResponseFactory.ok(response);
    }

    /* ================= REFRESH TOKEN ================= */

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
            @RequestBody String refreshToken
    ) {
        AuthResponse response =
                authService.refreshAccessToken(refreshToken);
        return ResponseFactory.ok(response);
    }

    @GetMapping("/hello/{name}") // Поменяли на GET и дали понятное имя переменной
    public ResponseEntity<String> sayHello(@PathVariable String name) {
        String message = String.format("Hello %s World", name); // Более чистый способ форматирования
        return ResponseEntity.ok(message);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Object>> forgotPassword(@RequestBody ForgotPasswordRequest req) {
        authService.forgotPassword(req);

        String message = messageService.get(MessageKey.RESET_CODE_SENT_SUCCESSFULLY);


        return ResponseFactory.ok(message);
    }

    @PostMapping("/verify-forgot-password")
    public ResponseEntity<ApiResponse<Object>> verifyForgot(@RequestBody VerifyForgotPasswordRequest req) {
        authService.verifyForgotPassword(req);

        String message = messageService.get(MessageKey.CODE_VERIFIED);

        return ResponseFactory.ok(message);
    }


    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Object>> resetPassword(@RequestBody ResetPasswordRequest req) {
        authService.resetPassword(req);

        String message = messageService.get(MessageKey.PASSWORD_RESET_SUCCESSFULLY);


        return ResponseFactory.ok(message);
    }
}
