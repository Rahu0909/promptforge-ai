package com.rahulagarwal.promptforge.ai.memory.service;

import com.rahulagarwal.promptforge.ai.memory.model.ConversationContext;

import java.util.UUID;

public interface AIConversationService {

    ConversationContext create(UUID projectId, UUID userId);

}