package com.rahulagarwal.promptforge.auth.service.impl;

import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.entity.PasswordResetToken;
import com.rahulagarwal.promptforge.auth.repository.PasswordResetTokenRepository;
import com.rahulagarwal.promptforge.auth.service.PasswordResetTokenService;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import com.rahulagarwal.promptforge.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository repository;
    private final TokenGenerator tokenGenerator;

    @Override
    public PasswordResetToken create(AuthUser user) {
        repository.deleteAllByAuthUserId(user.getId());
        PasswordResetToken token = new PasswordResetToken();
        token.setAuthUser(user);
        token.setToken(tokenGenerator.generate());
        token.setExpiresAt(OffsetDateTime.now().plusMinutes(15));
        token.setUsed(false);
        return repository.save(token);
    }
    @Override
    public PasswordResetToken verify(String token) {
        PasswordResetToken passwordResetToken = repository.findByTokenAndUsedFalse(token).orElseThrow(() -> new BadRequestException("Invalid password reset token.", ErrorCode.INVALID_TOKEN));
        if (passwordResetToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new BadRequestException("Password reset token expired.", ErrorCode.TOKEN_EXPIRED);
        }
        return passwordResetToken;
    }

    @Override
    public void markAsUsed(PasswordResetToken token) {
        token.setUsed(true);
        repository.save(token);
    }
}