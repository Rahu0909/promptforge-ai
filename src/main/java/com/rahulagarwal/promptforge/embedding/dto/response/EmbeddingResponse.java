package com.rahulagarwal.promptforge.embedding.dto.response;

import lombok.Builder;


@Builder
public record EmbeddingResponse(
        float[] embedding,

        Integer dimensions,

        String provider,

        String model

) {
}