package com.rahulagarwal.promptforge.auth.service.impl;

import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.entity.RefreshToken;
import com.rahulagarwal.promptforge.auth.repository.RefreshTokenRepository;
import com.rahulagarwal.promptforge.auth.service.RefreshTokenService;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository repository;

    @Override
    public RefreshToken create(AuthUser user, String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAuthUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiresAt(OffsetDateTime.now().plusDays(7));
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
        repository.findAllByAuthUserId(user.getId()).forEach(token -> token.setRevoked(true));
    }
}