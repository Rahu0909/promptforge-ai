package com.rahulagarwal.promptforge.ai.memory.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ConversationContext(

        String conversationId,

        UUID projectId,

        UUID userId

) {
}