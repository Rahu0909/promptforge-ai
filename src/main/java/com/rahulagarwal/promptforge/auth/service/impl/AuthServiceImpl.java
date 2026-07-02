package com.rahulagarwal.promptforge.auth.service.impl;

import com.rahulagarwal.promptforge.auth.dto.request.LoginRequest;
import com.rahulagarwal.promptforge.auth.dto.request.RegisterRequest;
import com.rahulagarwal.promptforge.auth.dto.response.LoginResponse;
import com.rahulagarwal.promptforge.auth.dto.response.RegisterResponse;
import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.enums.AccountStatus;
import com.rahulagarwal.promptforge.auth.enums.AuthProvider;
import com.rahulagarwal.promptforge.auth.mapper.AuthMapper;
import com.rahulagarwal.promptforge.auth.repository.AuthUserRepository;
import com.rahulagarwal.promptforge.auth.service.AuthService;
import com.rahulagarwal.promptforge.auth.service.RefreshTokenService;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import com.rahulagarwal.promptforge.security.jwt.JwtProperties;
import com.rahulagarwal.promptforge.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setEmailVerified(false);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        AuthUser savedUser = authUserRepository.save(user);
        log.info("User created successfully with id={}", savedUser.getId());
        return authMapper.toRegisterResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        AuthUser user = authUserRepository.findByEmail(request.email()).orElseThrow();
        String access = jwtService.generateAccessToken(user);
        String refresh = jwtService.generateRefreshToken(user);
        refreshTokenService.create(user, refresh);
        return new LoginResponse(
                access,
                refresh,
                "Bearer",
                jwtProperties.accessTokenExpiration(),
                user.getRole().name(),
                user.getEmail()
        );
    }
}