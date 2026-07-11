package com.rahulagarwal.promptforge.ai.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ChatRequest(

        @NotBlank
        String prompt,

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