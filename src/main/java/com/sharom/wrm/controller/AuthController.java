package com.sharom.wrm.controller;

import com.sharom.wrm.payload.AuthResponse;
import com.sharom.wrm.payload.LoginRequest;
import com.sharom.wrm.payload.RegisterRequest;
import com.sharom.wrm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService authService;

    /* ================= REGISTER ================= */

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* ================= LOGIN ================= */

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {
        AuthResponse response =
                authService.login(request.username(), request.password());
        return ResponseEntity.ok(response);
    }

    /* ================= REFRESH TOKEN ================= */

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestBody String refreshToken
    ) {
        AuthResponse response =
                authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}
