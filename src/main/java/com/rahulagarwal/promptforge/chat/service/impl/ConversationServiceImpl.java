package com.rahulagarwal.promptforge.chat.service.impl;

import com.rahulagarwal.promptforge.ai.config.AIProperties;
import com.rahulagarwal.promptforge.chat.dto.request.CreateConversationRequest;
import com.rahulagarwal.promptforge.chat.dto.request.RenameConversationRequest;
import com.rahulagarwal.promptforge.chat.dto.response.ConversationResponse;
import com.rahulagarwal.promptforge.chat.dto.response.ConversationStatsResponse;
import com.rahulagarwal.promptforge.chat.entity.Conversation;
import com.rahulagarwal.promptforge.chat.enums.ConversationStatus;
import com.rahulagarwal.promptforge.chat.mapper.ChatMapper;
import com.rahulagarwal.promptforge.chat.repository.ConversationRepository;
import com.rahulagarwal.promptforge.chat.repository.MessageRepository;
import com.rahulagarwal.promptforge.chat.service.ConversationService;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.security.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository repository;
    private final AuthorizationService authorizationService;
    private final ChatMapper mapper;
    private final AIProperties aiProperties;
    private final MessageRepository messageRepository;

    @Override
    public ConversationResponse create(CreateConversationRequest request) {
        Project project = authorizationService.getOwnedProject(request.projectId());
        Conversation conversation = new Conversation();
        conversation.setProject(project);
        conversation.setTitle(request.title().trim());
        conversation.setProvider(aiProperties.getProvider());
        conversation.setModel(aiProperties.getModel());
        conversation.setLastMessageAt(OffsetDateTime.now());
        return mapper.toConversationResponse(repository.save(conversation));
    }

    @Override
    @Transactional(readOnly = true)
    public ConversationResponse get(UUID conversationId) {
        Conversation conversation = repository.findByIdAndStatus(conversationId, ConversationStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException("Conversation not found.", ErrorCode.RESOURCE_NOT_FOUND));
        authorizationService.getOwnedProject(conversation.getProject().getId());
        return mapper.toConversationResponse(conversation);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ConversationResponse> getProjectConversations(UUID projectId, String search, int page, int size) {
        authorizationService.getOwnedProject(projectId);
        PageRequest pageable = PageRequest.of(page, size, Sort.by("lastMessageAt").descending());
        Page<Conversation> conversations;
        if (search == null || search.isBlank()) {
            conversations = repository.findByProjectIdAndStatus(projectId, ConversationStatus.ACTIVE, pageable);
        } else {
            conversations = repository.findByProjectIdAndStatusAndTitleContainingIgnoreCase(projectId, ConversationStatus.ACTIVE, search.trim(), pageable);
        }
        return new PageResponse<>(conversations.getContent().stream().map(mapper::toConversationResponse).toList(), conversations.getNumber(), conversations.getSize(), conversations.getTotalElements(), conversations.getTotalPages(), conversations.isFirst(), conversations.isLast());
    }

    @Override
    public ConversationResponse rename(UUID conversationId, RenameConversationRequest request) {
        Conversation conversation = repository.findByIdAndStatus(conversationId, ConversationStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException("Conversation not found.", ErrorCode.RESOURCE_NOT_FOUND));
        authorizationService.getOwnedProject(conversation.getProject().getId());
        conversation.setTitle(request.title().trim());
        return mapper.toConversationResponse(repository.save(conversation));
    }

    @Override
    public void archive(UUID conversationId) {
        Conversation conversation = repository.findByIdAndStatus(conversationId, ConversationStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException("Conversation not found.", ErrorCode.RESOURCE_NOT_FOUND));
        authorizationService.getOwnedProject(conversation.getProject().getId());
        conversation.setStatus(ConversationStatus.ARCHIVED);
        repository.save(conversation);
    }

    @Override
    @Transactional(readOnly = true)
    public ConversationStatsResponse getStats(UUID conversationId) {
        Conversation conversation = repository.findByIdAndStatus(conversationId, ConversationStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException("Conversation not found.", ErrorCode.RESOURCE_NOT_FOUND));
        authorizationService.getOwnedProject(conversation.getProject().getId());
        long count = messageRepository.countByConversationId(conversationId);
        return new ConversationStatsResponse(conversation.getId(), count, conversation.getLastMessageAt(), conversation.getProvider(), conversation.getModel());
    }
}