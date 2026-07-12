package com.rahulagarwal.promptforge.chat.dto.response;

import com.rahulagarwal.promptforge.chat.enums.MessageRole;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MessageResponse(
        UUID id,
        MessageRole role,
        String content,
        Integer tokenCount,
        Long generationTimeMs,
        OffsetDateTime createdAt
) {
}