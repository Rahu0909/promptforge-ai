package com.rahulagarwal.promptforge.rag.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RagChatResponse(

        String answer,

        List<CitationResponse> citations

) {
}