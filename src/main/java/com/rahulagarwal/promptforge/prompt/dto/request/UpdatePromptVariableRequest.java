package com.rahulagarwal.promptforge.prompt.dto.request;

import jakarta.validation.constraints.Size;

public record UpdatePromptVariableRequest(

        @Size(max = 500)
        String description,

        @Size(max = 1000)
        String defaultValue,

        Boolean required

) {
}