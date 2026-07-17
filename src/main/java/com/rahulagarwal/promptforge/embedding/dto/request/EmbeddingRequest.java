package com.rahulagarwal.promptforge.embedding.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmbeddingRequest(

        @NotBlank
        String text

) {
}