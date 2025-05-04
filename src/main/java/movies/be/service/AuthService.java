package movies.be.service;

import movies.be.dto.*;

public interface AuthService {
    AuthResponse sendVerificationCode(RegisterRequest request);
    AuthResponse verifyAndRegister(EmailVerificationRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse forgotPassword(ForgotPasswordRequest request);
    AuthResponse resetPassword(ResetPasswordRequest request);

}