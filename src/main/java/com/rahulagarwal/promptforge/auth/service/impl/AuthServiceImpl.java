package com.rahulagarwal.promptforge.auth.service.impl;

import com.rahulagarwal.promptforge.auth.dto.request.*;
import com.rahulagarwal.promptforge.auth.dto.response.LoginResponse;
import com.rahulagarwal.promptforge.auth.dto.response.RegisterResponse;
import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.entity.EmailVerificationToken;
import com.rahulagarwal.promptforge.auth.entity.PasswordResetToken;
import com.rahulagarwal.promptforge.auth.entity.RefreshToken;
import com.rahulagarwal.promptforge.auth.enums.AccountStatus;
import com.rahulagarwal.promptforge.auth.enums.AuthProvider;
import com.rahulagarwal.promptforge.auth.mapper.AuthMapper;
import com.rahulagarwal.promptforge.auth.repository.AuthUserRepository;
import com.rahulagarwal.promptforge.auth.service.AuthService;
import com.rahulagarwal.promptforge.auth.service.EmailVerificationTokenService;
import com.rahulagarwal.promptforge.auth.service.PasswordResetTokenService;
import com.rahulagarwal.promptforge.auth.service.RefreshTokenService;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.security.jwt.JwtProperties;
import com.rahulagarwal.promptforge.security.jwt.JwtService;
import com.rahulagarwal.promptforge.security.model.CustomUserDetails;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import com.rahulagarwal.promptforge.user.enums.UserStatus;
import com.rahulagarwal.promptforge.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final UserProfileRepository userProfileRepository;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        log.debug("Checking duplicate email {}", request.email());
        if (authUserRepository.existsByEmail(request.email())) {
            log.warn("Registration failed. Email already exists: {}", request.email());
            throw new BadRequestException("Email is already registered.", ErrorCode.BAD_REQUEST);
        }
        AuthUser user = new AuthUser();
        user.setEmail(request.email().trim().toLowerCase());
        user.updatePassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setAccountStatus(AccountStatus.PENDING_VERIFICATION);
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setEmailVerified(false);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        AuthUser savedUser = authUserRepository.save(user);
        EmailVerificationToken verificationToken = emailVerificationTokenService.create(savedUser);
        log.info("User created successfully with id={}", savedUser.getId());
        log.debug("Email verification token : {}", verificationToken.getToken());
        return authMapper.toRegisterResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        AuthUser user = authUserRepository.findByEmail(request.email()).orElseThrow(() -> new ResourceNotFoundException("User not found.", ErrorCode.USER_NOT_FOUND));
        if (!user.isEmailVerified()) {
            throw new BadRequestException("Please verify your email before logging in.", ErrorCode.UNAUTHORIZED);
        }
        Optional<UserProfile> profile = userProfileRepository.findByAuthUserId(user.getId());
        if (profile.isPresent() && profile.get().getStatus() == UserStatus.DELETED) {
            throw new BadRequestException("Profile has been deleted.", ErrorCode.ACCESS_DENIED);
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        String access = jwtService.generateAccessToken(user);
        String refresh = jwtService.generateRefreshToken(user);
        refreshTokenService.create(user, refresh);
        return new LoginResponse(access, refresh, "Bearer", jwtProperties.accessTokenExpiration(), user.getRole().name(), user.getEmail());
    }

    @Override
    public LoginResponse refresh(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verify(request.refreshToken());
        AuthUser user = refreshToken.getAuthUser();
        refreshTokenService.revoke(request.refreshToken());
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        refreshTokenService.create(user, newRefreshToken);
        return new LoginResponse(newAccessToken, newRefreshToken, "Bearer", jwtProperties.accessTokenExpiration(), user.getRole().name(), user.getEmail());
    }

    @Override
    public void logout(LogoutRequest request) {
        refreshTokenService.revoke(request.refreshToken());
    }

    @Override
    public void logoutAll(LogoutAllRequest request) {
        AuthUser user = authUserRepository.findByEmail(request.email()).orElseThrow();
        refreshTokenService.revokeAll(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        AuthUser user = authUserRepository.findById(currentUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found.", ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect.", ErrorCode.INVALID_CREDENTIALS);
        }
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new BadRequestException("New password cannot be same as current password.", ErrorCode.BAD_REQUEST);
        }
        user.updatePassword(passwordEncoder.encode(request.newPassword()));
        authUserRepository.save(user);
        refreshTokenService.revokeAll(user);
        log.info("Password changed successfully for user={}", user.getEmail());
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        AuthUser user = authUserRepository.findByEmail(request.email()).orElseThrow(() -> new ResourceNotFoundException("User not found.", ErrorCode.USER_NOT_FOUND));
        PasswordResetToken token = passwordResetTokenService.create(user);
        log.info("Password reset token generated for {}", user.getEmail());
        log.debug("Password reset token : {}", token.getToken());
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenService.verify(request.token());
        AuthUser user = resetToken.getAuthUser();
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new BadRequestException("New password cannot be the same as the current password.", ErrorCode.BAD_REQUEST);
        }
        user.updatePassword(passwordEncoder.encode(request.newPassword()));
        authUserRepository.save(user);
        passwordResetTokenService.markAsUsed(resetToken);
        refreshTokenService.revokeAll(user);
        log.info("Password reset successfully for {}", user.getEmail());
    }

    @Override
    public void verifyEmail(VerifyEmailRequest request) {
        EmailVerificationToken verificationToken = emailVerificationTokenService.verify(request.token());
        AuthUser user = verificationToken.getAuthUser();
        if (user.isEmailVerified()) {
            throw new BadRequestException("Email already verified.", ErrorCode.BAD_REQUEST);
        }
        user.verifyEmail();
        authUserRepository.save(user);
        emailVerificationTokenService.markVerified(verificationToken);
        log.info("Email verified successfully for {}", user.getEmail());
    }
}