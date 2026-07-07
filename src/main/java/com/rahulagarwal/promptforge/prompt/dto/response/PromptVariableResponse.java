package com.rahulagarwal.promptforge.prompt.dto.response;

import java.util.UUID;

public record PromptVariableResponse(

        UUID id,

        String variableName,

        String description,

        String defaultValue,

        Boolean required

) {
}