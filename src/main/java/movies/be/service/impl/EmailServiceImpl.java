/*
 * @ (#) EmailServiceImpl.java 1.0 5/4/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */
package movies.be.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import movies.be.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendResetPasswordEmail(String to, String resetLink) {
        logger.info("Sending reset password email to: {}", to);
        sendEmail(to, "Reset Your Password - AaaMovies",
                "<h2>Reset Your Password</h2>" +
                        "<p>Click the link below to reset your password:</p>" +
                        "<a href=\"" + resetLink + "\">Reset Password</a>" +
                        "<p>This link will expire in 1 hour.</p>" +
                        "<p>If you did not request a password reset, please ignore this email.</p>");
    }

    @Override
    public void sendVerificationEmail(String to, String code) {
        logger.info("Sending verification email to: {}", to);
        sendEmail(to, "Verify Your Email - AaaMovies",
                "<h2>Email Verification</h2>" +
                        "<p>Your verification code is: <b>" + code + "</b></p>" +
                        "<p>This code will expire in 60 seconds.</p>" +
                        "<p>If you did not request this, please ignore this email.</p>");
    }

    @Override
    public void sendWelcomeEmail(String to, String fullName) {
        logger.info("Sending welcome email to: {}", to);
        sendEmail(to, "Welcome to AaaMovies!",
                "<h2>Welcome, " + fullName + "!</h2>" +
                        "<p>Thank you for joining AaaMovies. Start exploring our movie collection now!</p>");
    }

    private void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}