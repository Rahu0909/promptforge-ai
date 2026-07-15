package com.rahulagarwal.promptforge.ai.memory.service;

import reactor.core.publisher.Flux;

public interface ChatMemoryService {

    String chat(String conversationId, String message);

    Flux<String> stream(String conversationId, String message);

}