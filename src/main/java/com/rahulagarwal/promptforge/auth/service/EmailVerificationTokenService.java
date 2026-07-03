package com.rahulagarwal.promptforge.auth.service;

import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.entity.EmailVerificationToken;

public interface EmailVerificationTokenService {

    EmailVerificationToken create(AuthUser user);

    EmailVerificationToken verify(String token);

    void markVerified(EmailVerificationToken token);

}