package com.rahulagarwal.promptforge.semantic.mapper;

import com.rahulagarwal.promptforge.rag.retrieval.RetrievedChunk;
import com.rahulagarwal.promptforge.semantic.dto.response.SemanticSearchResponse;
import org.springframework.stereotype.Component;

@Component
public class SemanticSearchMapper {

    public SemanticSearchResponse toResponse(RetrievedChunk chunk) {

        return SemanticSearchResponse.builder()
                .documentId(chunk.documentId())
                .documentName(chunk.documentName())
                .chunkIndex(chunk.chunkIndex())
                .content(chunk.content())
                .similarity(chunk.similarity())
                .build();
    }

}