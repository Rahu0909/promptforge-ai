package com.rahulagarwal.promptforge.rag.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RagChatRequest(

        @NotBlank
        String question,

        @Min(1)
        @Max(10)
        Integer topK
) {
}