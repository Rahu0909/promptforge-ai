package com.rahulagarwal.promptforge.auth.service;

import com.rahulagarwal.promptforge.auth.dto.request.*;
import com.rahulagarwal.promptforge.auth.dto.response.LoginResponse;
import com.rahulagarwal.promptforge.auth.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refresh(RefreshTokenRequest request);

    void logout(LogoutRequest request);

    void logoutAll(LogoutAllRequest request);

    void changePassword(ChangePasswordRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    void verifyEmail(VerifyEmailRequest request);
}