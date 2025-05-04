package movies.be.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import movies.be.dto.*;
import movies.be.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/send-verification")
    public ResponseEntity<AuthResponse> sendVerificationCode(@Valid @RequestBody RegisterRequest request) {
        logger.info("Received send-verification request for: {}", request.getEmail());
        return ResponseEntity.ok(authService.sendVerificationCode(request));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<AuthResponse> resendVerificationCode(@Valid @RequestBody RegisterRequest request) {
        logger.info("Received resend-verification request for: {}", request.getEmail());
        return ResponseEntity.ok(authService.resendVerificationCode(request));
    }

    @PostMapping("/verify-register")
    public ResponseEntity<AuthResponse> verifyAndRegister(@Valid @RequestBody EmailVerificationRequest request) {
        logger.info("Received verify-register request for: {}", request.getEmail());
        return ResponseEntity.ok(authService.verifyAndRegister(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Received login request for: {}", request.getEmail());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<AuthResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        logger.info("Received forgot-password request for: {}", request.getEmail());
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    @GetMapping("/reset-password")
    public ResponseEntity<AuthResponse> handleGetResetPassword(@RequestParam("token") String token) {
        logger.info("Received GET request for reset-password with token: {}", token);
        return ResponseEntity.ok(new AuthResponse(null, "Please use POST method to reset password with this token."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AuthResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        logger.info("Received reset-password request");
        return ResponseEntity.ok(authService.resetPassword(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletResponse response) {
        logger.info("Received logout request");

        // Xóa cookie chứa token
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        logger.info("Cleared token cookie on logout");
        return ResponseEntity.ok(new AuthResponse(null, "Logout successful. Cookies cleared."));
    }
}