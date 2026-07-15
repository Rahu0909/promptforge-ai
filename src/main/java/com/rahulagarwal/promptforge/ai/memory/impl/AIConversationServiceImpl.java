package com.rahulagarwal.promptforge.ai.memory.impl;

import com.rahulagarwal.promptforge.ai.memory.model.ConversationContext;
import com.rahulagarwal.promptforge.ai.memory.service.AIConversationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AIConversationServiceImpl implements AIConversationService {

    @Override
    public ConversationContext create(UUID projectId,
                                      UUID userId) {
        return ConversationContext.builder()
                .conversationId(UUID.randomUUID().toString())
                .projectId(projectId)
                .userId(userId)
                .build();
    }
}