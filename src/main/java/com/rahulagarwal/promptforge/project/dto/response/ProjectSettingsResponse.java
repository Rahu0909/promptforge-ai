package com.rahulagarwal.promptforge.project.dto.response;

import com.rahulagarwal.promptforge.project.enums.AiProvider;

public record ProjectSettingsResponse(
        AiProvider provider,
        String model,
        Double temperature,
        Integer maxTokens,
        Boolean streamingEnabled,
        Boolean ragEnabled,
        Boolean memoryEnabled
) {
}