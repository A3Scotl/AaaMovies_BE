package movies.be.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import movies.be.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendResetPasswordEmail(String to, String resetLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Reset Your Password - AaaMovies");
            helper.setText(
                    "<h2>Reset Your Password</h2>" +
                            "<p>Click the link below to reset your password:</p>" +
                            "<a href=\"" + resetLink + "\">Reset Password</a>" +
                            "<p>This link will expire in 1 hour.</p>" +
                            "<p>If you did not request a password reset, please ignore this email.</p>",
                    true
            );

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reset password email", e);
        }
    }

    @Override
    public void sendVerificationEmail(String to, String verificationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Email Verification - AaaMovies");
            helper.setText(
                    "<h2>Email Verification</h2>" +
                            "<p>Your verification code is: <strong>" + verificationCode + "</strong></p>" +
                            "<p>Please enter this code to complete your registration.</p>" +
                            "<p>This code will expire in 10 minutes.</p>",
                    true
            );

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
}