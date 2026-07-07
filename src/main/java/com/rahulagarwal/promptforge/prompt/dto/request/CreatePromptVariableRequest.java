package com.rahulagarwal.promptforge.prompt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePromptVariableRequest(

        @NotBlank
        @Size(max = 100)
        String variableName,

        @Size(max = 500)
        String description,

        @Size(max = 1000)
        String defaultValue,

        Boolean required

) {
}