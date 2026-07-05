package com.rahulagarwal.promptforge.user.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        UUID authUserId,
        String email,
        String firstName,
        String lastName,
        String displayName,
        String bio,
        String avatarUrl,
        String timezone,
        String language,
        OffsetDateTime createdAt
) {
}