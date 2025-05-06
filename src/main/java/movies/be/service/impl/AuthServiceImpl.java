/*
 * @ (#) AuthServiceImpl.java 1.0 5/4/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */
package movies.be.service.impl;

import movies.be.dto.*;
import movies.be.model.Role;
import movies.be.model.User;
import movies.be.model.VerificationToken;
import movies.be.repository.RoleRepository;
import movies.be.repository.UserRepository;
import movies.be.repository.VerificationTokenRepository;
import movies.be.security.JwtUtil;
import movies.be.service.AuthService;
import movies.be.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@EnableScheduling
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    // Biểu thức chính quy kiểm tra mật khẩu mạnh
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );

    // Giới hạn số lần gửi lại mã (3 lần trong 1 giờ)
    private static final int MAX_RESEND_COUNT = 3;
    private static final long RESEND_TIME_WINDOW = 3600000; // 1 giờ (ms)

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

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

        // Kiểm tra mật khẩu mạnh
        if (!isPasswordStrong(request.getPassword())) {
            logger.warn("Weak password for email: {}", request.getEmail());
            return new AuthResponse(null, "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character");
        }

        // Tạo mã xác thực 6 chữ số
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime timestamp = LocalDateTime.now();
        verificationTokenRepository.save(VerificationToken.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword())) // Mã hóa trước khi lưu
                .code(verificationCode)
                .timestamp(timestamp)
                .resendCount(0)
                .lastResendTime(timestamp)
                .build());

        // Gửi email
        emailService.sendVerificationEmail(request.getEmail(), verificationCode);
        logger.info("Verification code sent to: {}. Code expires in {} ms", request.getEmail(), verificationCodeExpiration);

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

        VerificationToken token = verificationTokenRepository.findByEmail(request.getEmail());
        if (token == null) {
            logger.warn("No pending registration found for email: {}", request.getEmail());
            return new AuthResponse(null, "No pending registration found");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isBefore(token.getLastResendTime().plusNanos(RESEND_TIME_WINDOW * 1000000))) {
            if (token.getResendCount() >= MAX_RESEND_COUNT) {
                logger.warn("Maximum resend attempts reached for email: {}", request.getEmail());
                return new AuthResponse(null, "Maximum resend attempts reached. Try again later.");
            }
        }

        // Tạo mã mới
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        token.setCode(verificationCode);
        token.setTimestamp(LocalDateTime.now());
        token.setResendCount(token.getResendCount() + 1);
        token.setLastResendTime(LocalDateTime.now());
        verificationTokenRepository.save(token);

        emailService.sendVerificationEmail(request.getEmail(), verificationCode);
        logger.info("Verification code resent to: {}. Resend count: {}, expires in {} ms", request.getEmail(), token.getResendCount(), verificationCodeExpiration);

        return new AuthResponse(null, "Verification code resent to email");
    }

    /**
     * Xác thực mã và hoàn tất đăng ký.
     */
    @Override
    public AuthResponse verifyAndRegister(EmailVerificationRequest request) {
        logger.info("Verifying registration for email: {}", request.getEmail());

        VerificationToken verificationToken = verificationTokenRepository.findByEmail(request.getEmail());
        if (verificationToken == null) {
            logger.warn("No verification code found for email: {}", request.getEmail());
            return new AuthResponse(null, "No verification code found");
        }

        // Kiểm tra thời gian hết hạn
        if (LocalDateTime.now().isAfter(verificationToken.getTimestamp().plusNanos(verificationCodeExpiration * 1000000))) {
            logger.warn("Verification code expired for email: {}", request.getEmail());
            return new AuthResponse(null, "Verification code expired");
        }

        if (!verificationToken.getCode().equals(request.getVerificationCode())) {
            logger.warn("Invalid verification code for email: {}", request.getEmail());
            return new AuthResponse(null, "Invalid verification code");
        }

        // Tạo tài khoản
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            logger.info("Creating new USER role");
            userRole = roleRepository.save(Role.builder().name("USER").build());
        }

        User user = User.builder()
                .email(request.getEmail())
                .fullName(verificationToken.getFullName())
                .password(verificationToken.getPassword()) // Mật khẩu đã được mã hóa trước đó
                .roles(Collections.singleton(userRole))
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();

        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
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
        logger.error("user: {}", user);
        logger.error("admin: {}", new BCryptPasswordEncoder().encode("admin"));
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.error("Invalid credentials for: {}", request.getEmail());
            return new AuthResponse(null, "Invalid credentials");
        }

        if (!user.isActive()) {
            logger.warn("Account is not active for: {}", request.getEmail());
            return new AuthResponse(null, "Account is not active");
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

        // Kiểm tra mật khẩu mạnh
        if (!isPasswordStrong(request.getPassword())) {
            logger.warn("Weak password during reset for email: {}", email);
            return new AuthResponse(null, "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        logger.info("Password reset successful for: {}", email);

        return new AuthResponse(null, "Password reset successful");
    }

    /**
     * Dọn dẹp các mã xác thực và đăng ký tạm thời đã hết hạn.
     */
    @Scheduled(fixedRate = 60000) // Chạy mỗi 60 giây
    public void cleanupExpiredData() {
        LocalDateTime currentTime = LocalDateTime.now();
        verificationTokenRepository.deleteByTimestampBefore(currentTime.minusNanos(verificationCodeExpiration * 1000000));
        logger.info("Cleaned up expired verification tokens");
    }

    /**
     * Kiểm tra mật khẩu có đủ mạnh không.
     */
    private boolean isPasswordStrong(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}