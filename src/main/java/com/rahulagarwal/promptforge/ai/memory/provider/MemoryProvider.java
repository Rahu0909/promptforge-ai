package com.rahulagarwal.promptforge.ai.memory.provider;

import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface MemoryProvider {

    void add(String conversationId, Message message);

    List<Message> get(String conversationId);

    void clear(String conversationId);

}