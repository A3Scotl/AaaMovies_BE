/*
 * @ (#) AuthServiceImpl.java 1.0 5/4/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */
package movies.be.service.impl;

import movies.be.dto.*;
import movies.be.model.Role;
import movies.be.model.User;
import movies.be.repository.RoleRepository;
import movies.be.repository.UserRepository;
import movies.be.security.JwtUtil;
import movies.be.service.AuthService;
import movies.be.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Value("${verification.code.expiration}")
    private long verificationCodeExpiration;

    // Lưu trữ mã xác thực và thời gian tạo
    private final Map<String, VerificationData> verificationDataMap = new HashMap<>();
    private final Map<String, RegisterRequest> pendingRegistrations = new HashMap<>();

    /**
     * Gửi mã xác thực qua email khi người dùng đăng ký.
     */
    @Override
    public AuthResponse sendVerificationCode(RegisterRequest request) {
        logger.info("Sending verification code for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Email already exists: {}", request.getEmail());
            return new AuthResponse(null, "Email already exists");
        }

        // Tạo mã xác thực 6 chữ số
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        verificationDataMap.put(request.getEmail(), new VerificationData(verificationCode, System.currentTimeMillis()));
        pendingRegistrations.put(request.getEmail(), request);

        // Gửi email
        emailService.sendVerificationEmail(request.getEmail(), verificationCode);
        logger.info("Verification code sent to: {}", request.getEmail());

        return new AuthResponse(null, "Verification code sent to email");
    }

    /**
     * Gửi lại mã xác thực nếu mã cũ hết hạn hoặc người dùng yêu cầu.
     */
    @Override
    public AuthResponse resendVerificationCode(RegisterRequest request) {
        logger.info("Resending verification code for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Email already exists: {}", request.getEmail());
            return new AuthResponse(null, "Email already exists");
        }

        if (!pendingRegistrations.containsKey(request.getEmail())) {
            logger.warn("No pending registration found for email: {}", request.getEmail());
            return new AuthResponse(null, "No pending registration found");
        }

        // Tạo mã mới
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        verificationDataMap.put(request.getEmail(), new VerificationData(verificationCode, System.currentTimeMillis()));
        emailService.sendVerificationEmail(request.getEmail(), verificationCode);
        logger.info("Verification code resent to: {}", request.getEmail());

        return new AuthResponse(null, "Verification code resent to email");
    }

    /**
     * Xác thực mã và hoàn tất đăng ký.
     */
    @Override
    public AuthResponse verifyAndRegister(EmailVerificationRequest request) {
        logger.info("Verifying registration for email: {}", request.getEmail());

        VerificationData verificationData = verificationDataMap.get(request.getEmail());
        if (verificationData == null) {
            logger.warn("No verification code found for email: {}", request.getEmail());
            return new AuthResponse(null, "No verification code found");
        }

        // Kiểm tra thời gian hết hạn (60 giây)
        if (System.currentTimeMillis() - verificationData.timestamp > verificationCodeExpiration) {
            logger.warn("Verification code expired for email: {}", request.getEmail());
            return new AuthResponse(null, "Verification code expired");
        }

        if (!verificationData.code.equals(request.getVerificationCode())) {
            logger.warn("Invalid verification code for email: {}", request.getEmail());
            return new AuthResponse(null, "Invalid verification code");
        }

        RegisterRequest registerRequest = pendingRegistrations.get(request.getEmail());
        if (registerRequest == null) {
            logger.error("No pending registration found for email: {}", request.getEmail());
            return new AuthResponse(null, "No pending registration found");
        }

        // Xóa dữ liệu tạm
        verificationDataMap.remove(request.getEmail());
        pendingRegistrations.remove(request.getEmail());

        // Tạo tài khoản
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            logger.info("Creating new USER role");
            userRole = roleRepository.save(Role.builder().name("USER").build());
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Collections.singleton(userRole))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        logger.info("User registered successfully: {}", user.getEmail());

        // Gửi email chào mừng
        try {
            emailService.sendWelcomeEmail(user.getEmail(), user.getFullName());
            logger.info("Welcome email sent to: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Failed to send welcome email: {}", e.getMessage());
        }

        String token = jwtUtil.generateToken(user.getEmail(), "USER");
        return new AuthResponse(token, "Registration successful");
    }

    /**
     * Xử lý đăng nhập người dùng.
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        logger.info("Login attempt for: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail().toLowerCase());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.error("Invalid credentials for: {}", request.getEmail());
            return new AuthResponse(null, "Invalid credentials");
        }

        // Xác thực với AuthenticationManager
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail().toLowerCase(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.warn("Authentication failed but continuing: {}", e.getMessage());
        }

        String role = user.getRoles().stream().findFirst().map(Role::getName).orElse("USER");
        String token = jwtUtil.generateToken(user.getEmail(), role);
        logger.info("Login successful for: {}, role: {}", user.getEmail(), role);

        return new AuthResponse(token, "Login successful");
    }

    /**
     * Yêu cầu đặt lại mật khẩu.
     */
    @Override
    public AuthResponse forgotPassword(ForgotPasswordRequest request) {
        logger.info("Forgot password request for: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            logger.warn("Email not found: {}", request.getEmail());
            return new AuthResponse(null, "Email not found");
        }

        String token = jwtUtil.generateResetPasswordToken(user.getEmail());
        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token;
        emailService.sendResetPasswordEmail(user.getEmail(), resetLink);
        logger.info("Reset password link sent to: {}", user.getEmail());

        return new AuthResponse(null, "Reset password link sent to email");
    }

    /**
     * Đặt lại mật khẩu bằng token.
     */
    @Override
    public AuthResponse resetPassword(ResetPasswordRequest request) {
        logger.info("Reset password attempt");

        if (!jwtUtil.validateToken(request.getToken())) {
            logger.warn("Invalid or expired reset token");
            return new AuthResponse(null, "Invalid or expired token");
        }

        String email = jwtUtil.getEmailFromToken(request.getToken());
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.warn("User not found for email: {}", email);
            return new AuthResponse(null, "User not found");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        logger.info("Password reset successful for: {}", email);

        return new AuthResponse(null, "Password reset successful");
    }

    /**
     * Lớp dữ liệu nội bộ để lưu mã xác thực và thời gian tạo.
     */
    private static class VerificationData {
        private final String code;
        private final long timestamp;

        public VerificationData(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
    }
}