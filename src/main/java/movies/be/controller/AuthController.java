/*
 * @ (#) AuthController.java 1.0 2025-05-05
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package movies.be.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import movies.be.dto.*;
import movies.be.exception.AuthException;
import movies.be.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/send-verification")
    public ResponseEntity<?> sendVerificationCode(@Valid @RequestBody RegisterRequest request) {
        logger.info("Received send-verification request for: {}", request.getEmail());
        try {
            AuthResponse response = authService.sendVerificationCode(request);
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            logger.error("Failed to send verification code for {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }
    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerificationCode(@Valid @RequestBody RegisterRequest request) {
        logger.info("Received resend-verification request for: {}", request.getEmail());
        try {
            AuthResponse response = authService.resendVerificationCode(request);
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            logger.error("Failed to resend verification code for {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/verify-register")
    public ResponseEntity<?> verifyAndRegister(@Valid @RequestBody EmailVerificationRequest request) {
        logger.info("Received verify-register request for: {}", request.getEmail());
        try {
            AuthResponse response = authService.verifyAndRegister(request);
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            logger.error("Failed to verify and register for {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Received login request for: {}", request.getEmail());
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            logger.error("Failed to login for {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError(HttpStatus.UNAUTHORIZED, e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        logger.info("Received forgot-password request for: {}", request.getEmail());
        try {
            AuthResponse response = authService.forgotPassword(request);
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            logger.error("Failed to process forgot-password for {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/reset-password")
    public ResponseEntity<AuthResponse> handleGetResetPassword(@RequestParam("token") String token) {
        logger.info("Received GET request for reset-password with token: {}", token);
        return ResponseEntity.ok(new AuthResponse(null, "Please use POST method to reset password with this token."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        logger.info("Received reset-password request");
        try {
            AuthResponse response = authService.resetPassword(request);
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            logger.error("Failed to reset password: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletResponse response) {
        logger.info("Received logout request");

        // Clear the token cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        logger.info("Cleared token cookie on logout");
        return ResponseEntity.ok(new AuthResponse(null, "Logout successful. Cookies cleared."));
    }
}