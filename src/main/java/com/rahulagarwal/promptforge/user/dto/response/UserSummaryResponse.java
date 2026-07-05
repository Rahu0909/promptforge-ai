package com.rahulagarwal.promptforge.user.dto.response;

import com.rahulagarwal.promptforge.user.enums.UserStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserSummaryResponse(

        UUID id,

        String displayName,

        String email,

        UserStatus status,

        OffsetDateTime createdAt

) {
}