/*
 * @ (#) EmailService.java 1.0 5/4/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */
package movies.be.service;

public interface EmailService {
    void sendResetPasswordEmail(String to, String resetLink);
    void sendVerificationEmail(String to, String verificationCode);
}
