package com.rahulagarwal.promptforge.ai.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ChatRequest(

        @NotBlank
        String prompt,

        @NotBlank(message = "Conversation ID is required.")
        String conversationId,

        @Min(1)
        @Max(20)
        Integer lines,

        @DecimalMin("0.0")
        @DecimalMax("2.0")
        Double temperature,

        @DecimalMin("0.0")
        @DecimalMax("1.0")
        Double topP,

        @Min(1)
        @Max(100)
        Integer topK,

        @Min(50)
        @Max(8192)
        Integer maxTokens

) {
}