package com.rahulagarwal.promptforge.project.dto.request;

import com.rahulagarwal.promptforge.project.enums.AiProvider;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateProjectSettingsRequest(

        AiProvider provider,

        @Size(max = 100)
        String model,

        @DecimalMin("0.0")
        @DecimalMax("2.0")
        Double temperature,

        @Min(1)
        @Max(32768)
        Integer maxTokens,

        Boolean streamingEnabled,

        Boolean ragEnabled,

        Boolean memoryEnabled

) {
}