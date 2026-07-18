package com.rahulagarwal.promptforge.vector.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SearchResult(

        UUID embeddingId,

        UUID sourceId,

        String content,

        Double similarity

) {
}