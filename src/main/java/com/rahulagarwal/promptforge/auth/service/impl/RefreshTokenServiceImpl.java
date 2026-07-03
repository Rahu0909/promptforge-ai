package com.rahulagarwal.promptforge.auth.service.impl;

import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.entity.RefreshToken;
import com.rahulagarwal.promptforge.auth.repository.RefreshTokenRepository;
import com.rahulagarwal.promptforge.auth.service.RefreshTokenService;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import com.rahulagarwal.promptforge.security.jwt.JwtProperties;
import com.rahulagarwal.promptforge.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final JwtProperties jwtProperties;
    private final JwtService jwtService;

    @Override
    public RefreshToken create(AuthUser user, String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAuthUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiresAt(jwtService.refreshTokenExpiry());
        return repository.save(refreshToken);
    }

    @Override
    public RefreshToken verify(String token) {
        RefreshToken refreshToken = repository.findByToken(token).orElseThrow(() -> new BadRequestException("Invalid refresh token.", ErrorCode.INVALID_TOKEN));
        if (refreshToken.isRevoked()) {
            throw new BadRequestException("Refresh token revoked.", ErrorCode.INVALID_TOKEN);
        }
        if (refreshToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new BadRequestException("Refresh token expired.", ErrorCode.TOKEN_EXPIRED);
        }
        return refreshToken;
    }

    @Override
    public void revoke(String token) {
        repository.findByToken(token).ifPresent(refresh -> {
            refresh.setRevoked(true);
            repository.save(refresh);
        });
    }

    @Override
    public void revokeAll(AuthUser user) {
        List<RefreshToken> tokens = repository.findAllByAuthUserId(user.getId());
        tokens.forEach(token -> token.setRevoked(true));
        repository.saveAll(tokens);
    }
}