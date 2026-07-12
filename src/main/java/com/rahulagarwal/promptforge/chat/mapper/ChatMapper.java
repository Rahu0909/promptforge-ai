package com.rahulagarwal.promptforge.chat.mapper;

import com.rahulagarwal.promptforge.chat.dto.response.ConversationResponse;
import com.rahulagarwal.promptforge.chat.dto.response.MessageResponse;
import com.rahulagarwal.promptforge.chat.entity.Conversation;
import com.rahulagarwal.promptforge.chat.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public ConversationResponse toConversationResponse(Conversation conversation) {
        return new ConversationResponse(
                conversation.getId(),
                conversation.getProject().getId(),
                conversation.getTitle(),
                conversation.getProvider(),
                conversation.getModel(),
                conversation.getStatus(),
                conversation.getLastMessageAt(),
                conversation.getCreatedAt()
        );
    }

    public MessageResponse toMessageResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getRole(),
                message.getContent(),
                message.getTokenCount(),
                message.getGenerationTimeMs(),
                message.getCreatedAt()
        );
    }

}