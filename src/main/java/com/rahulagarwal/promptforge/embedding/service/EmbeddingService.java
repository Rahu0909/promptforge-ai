package com.rahulagarwal.promptforge.embedding.service;

import com.rahulagarwal.promptforge.embedding.dto.request.EmbeddingRequest;
import com.rahulagarwal.promptforge.embedding.dto.response.EmbeddingResponse;

public interface EmbeddingService {

    EmbeddingResponse generateEmbedding(EmbeddingRequest request);

}