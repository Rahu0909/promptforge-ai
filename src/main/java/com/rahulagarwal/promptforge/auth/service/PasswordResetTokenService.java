package com.rahulagarwal.promptforge.auth.service;

import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.entity.PasswordResetToken;

public interface PasswordResetTokenService {

    PasswordResetToken create(AuthUser user);

    PasswordResetToken verify(String token);

    void markAsUsed(PasswordResetToken token);
}