package com.rahulagarwal.promptforge.auth.service.impl;

import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.entity.EmailVerificationToken;
import com.rahulagarwal.promptforge.auth.repository.EmailVerificationTokenRepository;
import com.rahulagarwal.promptforge.auth.service.EmailVerificationTokenService;
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
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private final EmailVerificationTokenRepository repository;
    private final TokenGenerator tokenGenerator;

    @Override
    public EmailVerificationToken create(AuthUser user) {
        repository.deleteAllByAuthUserId(user.getId());
        EmailVerificationToken token = new EmailVerificationToken();
        token.setAuthUser(user);
        token.setToken(tokenGenerator.generate());
        token.setExpiresAt(OffsetDateTime.now().plusHours(24));
        return repository.save(token);
    }

    @Override
    public EmailVerificationToken verify(String token) {
        EmailVerificationToken verification = repository.findByTokenAndVerifiedFalse(token).orElseThrow(() -> new BadRequestException("Invalid verification token.", ErrorCode.INVALID_TOKEN));
        if (verification.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new BadRequestException("Verification token expired.", ErrorCode.TOKEN_EXPIRED);
        }
        return verification;
    }

    @Override
    public void markVerified(EmailVerificationToken token) {
        token.setVerified(true);
        repository.save(token);
    }
}