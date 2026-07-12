package com.rahulagarwal.promptforge.chat.service;

import com.rahulagarwal.promptforge.chat.dto.request.SendMessageRequest;
import com.rahulagarwal.promptforge.chat.dto.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    MessageResponse sendMessage(UUID conversationId, SendMessageRequest request);

    List<MessageResponse> getConversationMessages(UUID conversationId);

}