package movies.be.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import movies.be.dto.*;
import movies.be.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/send-verification")
    public ResponseEntity<AuthResponse> sendVerificationCode(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.sendVerificationCode(request));
    }

    @PostMapping("/verify-register")
    public ResponseEntity<AuthResponse> verifyAndRegister(@Valid @RequestBody EmailVerificationRequest request) {
        return ResponseEntity.ok(authService.verifyAndRegister(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<AuthResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AuthResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout() {
        return ResponseEntity.ok(new AuthResponse(null, "Logout successful. Please remove JWT from client."));
    }
}