package movies.be.service;

public interface EmailService {
    void sendResetPasswordEmail(String to, String resetLink);
    void sendVerificationEmail(String to, String code);
    void sendWelcomeEmail(String to, String fullName);
}