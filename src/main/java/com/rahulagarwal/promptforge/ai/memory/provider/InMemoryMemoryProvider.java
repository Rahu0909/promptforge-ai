package com.rahulagarwal.promptforge.ai.memory.provider;

import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryMemoryProvider implements MemoryProvider {
    private final Map<String, List<Message>> memory = new ConcurrentHashMap<>();

    @Override
    public void add(String conversationId, Message message) {
        memory.computeIfAbsent(conversationId, id -> new ArrayList<>()).add(message);
    }

    @Override
    public List<Message> get(String conversationId) {
        return memory.getOrDefault(conversationId, List.of());
    }

    @Override
    public void clear(String conversationId) {
        memory.remove(conversationId);
    }

}