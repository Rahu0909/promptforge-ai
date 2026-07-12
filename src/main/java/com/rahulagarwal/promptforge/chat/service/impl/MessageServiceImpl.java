package com.rahulagarwal.promptforge.chat.service.impl;

import com.rahulagarwal.promptforge.ai.config.AIProperties;
import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import com.rahulagarwal.promptforge.ai.factory.AIRequestFactory;
import com.rahulagarwal.promptforge.ai.service.AIService;
import com.rahulagarwal.promptforge.chat.builder.ConversationHistoryBuilder;
import com.rahulagarwal.promptforge.chat.dto.request.SendMessageRequest;
import com.rahulagarwal.promptforge.chat.dto.response.MessageResponse;
import com.rahulagarwal.promptforge.chat.entity.Conversation;
import com.rahulagarwal.promptforge.chat.entity.Message;
import com.rahulagarwal.promptforge.chat.enums.ConversationStatus;
import com.rahulagarwal.promptforge.chat.enums.MessageRole;
import com.rahulagarwal.promptforge.chat.mapper.ChatMapper;
import com.rahulagarwal.promptforge.chat.repository.ConversationRepository;
import com.rahulagarwal.promptforge.chat.repository.MessageRepository;
import com.rahulagarwal.promptforge.chat.service.MessageService;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.security.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final AuthorizationService authorizationService;
    private final ChatMapper mapper;
    private final AIService aiService;
    private final AIRequestFactory aiRequestFactory;
    private final ConversationHistoryBuilder historyBuilder;

    @Override
    public MessageResponse sendMessage(UUID conversationId, SendMessageRequest request) {
        Conversation conversation = conversationRepository.findByIdAndStatus(conversationId, ConversationStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException("Conversation not found.", ErrorCode.RESOURCE_NOT_FOUND));
        authorizationService.getOwnedProject(conversation.getProject().getId());
        Message userMessage = new Message();
        userMessage.setConversation(conversation);
        userMessage.setRole(MessageRole.USER);
        userMessage.setContent(request.message().trim());
        messageRepository.save(userMessage);
        List<Message> history = messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId);
        String finalPrompt = historyBuilder.build(history, request.message().trim());
        ChatRequest chatRequest = aiRequestFactory.create(finalPrompt);
        long start = System.currentTimeMillis();
        String aiResponse = aiService.generate(finalPrompt, chatRequest);
        long generationTime = System.currentTimeMillis() - start;
        Message assistant = new Message();
        assistant.setConversation(conversation);
        assistant.setRole(MessageRole.ASSISTANT);
        assistant.setContent(aiResponse);
        assistant.setGenerationTimeMs(generationTime);
        messageRepository.save(assistant);
        OffsetDateTime now = OffsetDateTime.now();
        conversation.setLastMessageAt(now);
        conversationRepository.save(conversation);
        return mapper.toMessageResponse(assistant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponse> getConversationMessages(UUID conversationId) {
        Conversation conversation = conversationRepository.findByIdAndStatus(conversationId, ConversationStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException("Conversation not found.", ErrorCode.RESOURCE_NOT_FOUND));
        authorizationService.getOwnedProject(conversation.getProject().getId());
        return messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId).stream().map(mapper::toMessageResponse).toList();
    }
}