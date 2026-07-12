package com.rahulagarwal.promptforge.chat.builder;

import com.rahulagarwal.promptforge.chat.entity.Message;
import com.rahulagarwal.promptforge.chat.enums.MessageRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConversationHistoryBuilder {

    public String build(List<Message> history, String latestPrompt) {
        StringBuilder builder = new StringBuilder();
        builder.append("""
                You are PromptForge AI.
                Follow previous context carefully.
                Conversation History:
                """);
        for (Message message : history) {
            if (message.getRole() == MessageRole.USER) {
                builder.append("USER: ");
            } else {
                builder.append("ASSISTANT: ");
            }
            builder.append(message.getContent()).append("\n\n");
        }
        builder.append("USER: ").append(latestPrompt);
        return builder.toString();
    }
}