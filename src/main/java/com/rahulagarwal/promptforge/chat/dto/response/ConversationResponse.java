package com.rahulagarwal.promptforge.chat.dto.response;

import com.rahulagarwal.promptforge.chat.enums.ConversationStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ConversationResponse(
        UUID id,
        UUID projectId,
        String title,
        String provider,
        String model,
        ConversationStatus status,
        OffsetDateTime lastMessageAt,
        OffsetDateTime createdAt
) {
}