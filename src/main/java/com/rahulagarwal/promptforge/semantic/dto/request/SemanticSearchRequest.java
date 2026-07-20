package com.rahulagarwal.promptforge.semantic.dto.request;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record SemanticSearchRequest(

        @NotNull(message = "Project id is required")
        UUID projectId,

        @NotBlank(message = "Search query cannot be blank")
        String query,

        @Min(1)
        @Max(20)
        Integer limit,

        @DecimalMin("0.0")
        @DecimalMax("1.0")
        Double minimumSimilarity
) {}