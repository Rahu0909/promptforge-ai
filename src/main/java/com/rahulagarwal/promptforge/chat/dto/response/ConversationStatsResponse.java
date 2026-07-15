package com.rahulagarwal.promptforge.chat.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ConversationStatsResponse(

        UUID conversationId,

        Long totalMessages,

        OffsetDateTime lastMessageAt,

        String provider,

        String model

) {
}