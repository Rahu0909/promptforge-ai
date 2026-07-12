package com.rahulagarwal.promptforge.ai.factory;

import com.rahulagarwal.promptforge.ai.config.AIProperties;
import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AIRequestFactory {

    private final AIProperties aiProperties;

    public ChatRequest create(String prompt) {

        return ChatRequest.builder()
                .prompt(prompt)
                .temperature(aiProperties.getTemperature())
                .topP(aiProperties.getTopP())
                .topK(aiProperties.getTopK())
                .maxTokens(aiProperties.getMaxTokens())
                .build();
    }

}