package com.rahulagarwal.promptforge.auth.dto.response;

import com.rahulagarwal.promptforge.auth.enums.AccountStatus;
import com.rahulagarwal.promptforge.auth.enums.AuthProvider;
import com.rahulagarwal.promptforge.user.enums.UserRole;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RegisterResponse(
        UUID id,
        String email,
        UserRole role,
        AccountStatus accountStatus,
        AuthProvider authProvider,
        boolean emailVerified,
        OffsetDateTime createdAt
) {
}