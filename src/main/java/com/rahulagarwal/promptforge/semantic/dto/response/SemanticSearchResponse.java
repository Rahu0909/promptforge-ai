package com.rahulagarwal.promptforge.semantic.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SemanticSearchResponse(

        UUID documentId,

        String documentName,

        Integer chunkIndex,

        String content,

        Double similarity

) {
}