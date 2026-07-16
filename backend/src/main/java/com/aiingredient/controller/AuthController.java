package com.aiingredient.controller;

import com.aiingredient.dto.ApiResponse;
import com.aiingredient.dto.LoginRequest;
import com.aiingredient.dto.LoginResponse;
import com.aiingredient.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(ApiResponse.success(authService.login(request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, e.getMessage()));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody LoginRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, e.getMessage()));
        }
    }
}
