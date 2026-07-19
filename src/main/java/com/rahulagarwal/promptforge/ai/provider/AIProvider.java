package com.rahulagarwal.promptforge.ai.provider;

import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import reactor.core.publisher.Flux;

public interface AIProvider {

    String generate(ChatRequest request, String prompt);

    String generateRag(ChatRequest request, String prompt);

    Flux<String> stream(ChatRequest request, String prompt);

    <T> T generateStructured(ChatRequest request,
                             String prompt,
                             Class<T> responseType);

    String providerName();

}