package com.rahulagarwal.promptforge.chat.service;

import com.rahulagarwal.promptforge.chat.dto.request.CreateConversationRequest;
import com.rahulagarwal.promptforge.chat.dto.request.RenameConversationRequest;
import com.rahulagarwal.promptforge.chat.dto.response.ConversationResponse;
import com.rahulagarwal.promptforge.chat.dto.response.ConversationStatsResponse;
import com.rahulagarwal.promptforge.common.response.PageResponse;

import java.util.UUID;

public interface ConversationService {

    ConversationResponse create(CreateConversationRequest request);

    ConversationResponse get(UUID conversationId);

    PageResponse<ConversationResponse> getProjectConversations(UUID projectId, String search, int page, int size);

    ConversationResponse rename(UUID conversationId, RenameConversationRequest request);

    void archive(UUID conversationId);

    ConversationStatsResponse getStats(UUID conversationId);

}