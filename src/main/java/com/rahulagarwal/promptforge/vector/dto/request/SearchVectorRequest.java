package com.rahulagarwal.promptforge.vector.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SearchVectorRequest(

        @NotNull
        UUID projectId,

        @NotBlank
        String query,

        @Min(1)
        @Max(20)
        Integer topK

) {
}