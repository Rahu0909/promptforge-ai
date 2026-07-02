package com.rahulagarwal.promptforge.auth.service;

import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken create(AuthUser user, String token);

    RefreshToken verify(String token);

    void revoke(String token);

    void revokeAll(AuthUser user);

}