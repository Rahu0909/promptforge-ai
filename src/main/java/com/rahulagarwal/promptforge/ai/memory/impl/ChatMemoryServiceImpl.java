package com.rahulagarwal.promptforge.ai.memory.impl;

import com.rahulagarwal.promptforge.ai.memory.service.ChatMemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatMemoryServiceImpl implements ChatMemoryService {

    private final ChatClient chatClient;

    @Override
    public String chat(String conversationId,
                       String message) {
        System.out.println("ConversationId = " + conversationId);
        return chatClient
                .prompt()
                .advisors(advisor ->
                        advisor.param(
                                ChatMemory.CONVERSATION_ID,
                                conversationId))
                .user(message)
                .call()
                .content();
    }

    @Override
    public Flux<String> stream(String conversationId, String message) {
        return chatClient
                .prompt()
                .advisors(advisor ->
                        advisor.param(
                                ChatMemory.CONVERSATION_ID,
                                conversationId))
                .user(message)
                .stream()
                .content();
    }
}