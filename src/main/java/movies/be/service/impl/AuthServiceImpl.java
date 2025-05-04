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

    @Override
    public AuthResponse forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return new AuthResponse(null, "Email not found");
        }

        String token = jwtUtil.generateResetPasswordToken(user.getEmail());
        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token;
        emailService.sendResetPasswordEmail(user.getEmail(), resetLink);

        return new AuthResponse(null, "Reset password link sent to email");
    }

    @Override
    public AuthResponse resetPassword(ResetPasswordRequest request) {
        if (!jwtUtil.validateToken(request.getToken())) {
            return new AuthResponse(null, "Invalid or expired token");
        }

        String email = jwtUtil.getEmailFromToken(request.getToken());
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new AuthResponse(null, "User not found");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return new AuthResponse(null, "Password reset successful");
    }
    // Lưu trữ tạm thời mã xác thực và thông tin đăng ký
    private final Map<String, String> verificationCodes = new HashMap<>();
    private final Map<String, RegisterRequest> pendingRegistrations = new HashMap<>();

    @Override
    public AuthResponse sendVerificationCode(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, "Email already exists");
        }

        // Tạo mã xác thực 6 chữ số
        String verificationCode = String.format("%06d", new Random().nextInt(999999));

        // Lưu mã xác thực và thông tin đăng ký
        verificationCodes.put(request.getEmail(), verificationCode);
        pendingRegistrations.put(request.getEmail(), request);

        // Gửi email xác thực
        emailService.sendVerificationEmail(request.getEmail(), verificationCode);

        return new AuthResponse(null, "Verification code sent to email");
    }

    @Override
    public AuthResponse verifyAndRegister(EmailVerificationRequest request) {
        logger.info("Bắt đầu xác thực đăng ký cho email: {}", request.getEmail());

        String storedCode = verificationCodes.get(request.getEmail());
        if (storedCode == null || !storedCode.equals(request.getVerificationCode())) {
            logger.warn("Mã xác thực không hợp lệ cho email: {}", request.getEmail());
            return new AuthResponse(null, "Mã xác thực không hợp lệ");
        }

        RegisterRequest registerRequest = pendingRegistrations.get(request.getEmail());
        if (registerRequest == null) {
            logger.error("Không tìm thấy thông tin đăng ký cho email: {}", request.getEmail());
            return new AuthResponse(null, "Không tìm thấy thông tin đăng ký");
        }

        // Xóa dữ liệu tạm
        verificationCodes.remove(request.getEmail());
        pendingRegistrations.remove(request.getEmail());

        // Tạo tài khoản
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            logger.info("Tạo role USER mới vì không tìm thấy trong DB");
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
        logger.info("Đã tạo tài khoản thành công cho user: {}", user.getEmail());

        try {
            emailService.sendVerificationEmail(user.getEmail(), user.getFullName());
            logger.info("Đã gửi email chào mừng đến: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Không thể gửi email chào mừng: {}", e.getMessage());
        }

        String token = jwtUtil.generateToken(user.getEmail(), "USER");
        return new AuthResponse(token, "Đăng ký thành công");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        logger.info("Attempt login for: {}", request.getEmail());

        try {
            // Kiểm tra user tồn tại trước
            User user = userRepository.findByEmail(request.getEmail().toLowerCase());
            if (user == null) {
                logger.error("User not found: {}", request.getEmail());
                return new AuthResponse(null, "Invalid credentials");
            }

            // Kiểm tra password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                logger.error("Password mismatch for: {}", request.getEmail());
                return new AuthResponse(null, "Invalid credentials");
            }

            // Xác thực - comment đoạn này lại nếu gặp vấn đề
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail().toLowerCase(),
                                request.getPassword()
                        )
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.warn("Authentication failed but continuing token generation: {}", e.getMessage());
                // Tiếp tục xử lý để tạo token ngay cả khi xác thực thất bại
            }

            // Lấy role - đảm bảo có tiền tố ROLE_ cho Spring Security
            String role = user.getRoles().stream()
                    .findFirst()
                    .map(Role::getName)
                    .orElse("USER");

            // Không thêm "ROLE_" vào token, chỉ cần trong SecurityContext
            String token = jwtUtil.generateToken(user.getEmail(), role);
            logger.info("Login successful for: {}, with role: {}", user.getEmail(), role);

            return new AuthResponse(token, "Login successful");
        } catch (Exception e) {
            logger.error("Login failed for {}: {}", request.getEmail(), e.getMessage());
            return new AuthResponse(null, "Login failed: " + e.getMessage());
        }
    }
}