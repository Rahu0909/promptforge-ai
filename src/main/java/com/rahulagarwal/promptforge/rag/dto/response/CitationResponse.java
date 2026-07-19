package com.rahulagarwal.promptforge.rag.dto.response;

import lombok.Builder;

@Builder
public record CitationResponse(

        String documentName,

        Integer chunkIndex,

        Double similarity

) {
}