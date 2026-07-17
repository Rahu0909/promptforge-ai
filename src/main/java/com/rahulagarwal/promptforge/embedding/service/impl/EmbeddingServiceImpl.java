package com.rahulagarwal.promptforge.embedding.service.impl;

import com.rahulagarwal.promptforge.embedding.dto.request.EmbeddingRequest;
import com.rahulagarwal.promptforge.embedding.dto.response.EmbeddingResponse;
import com.rahulagarwal.promptforge.embedding.provider.EmbeddingProvider;
import com.rahulagarwal.promptforge.embedding.provider.EmbeddingProviderFactory;
import com.rahulagarwal.promptforge.embedding.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {
    private final EmbeddingProviderFactory providerFactory;

    @Override
    public EmbeddingResponse generateEmbedding(EmbeddingRequest request) {
        log.info("Generating embedding");
        EmbeddingProvider provider = providerFactory.getProvider();
        float[] embedding = provider.generateEmbedding(request.text());
        return EmbeddingResponse.builder().embedding(embedding).dimensions(embedding.length).provider(provider.providerName()).model(provider.modelName())      // We'll improve this shortly.
                .build();
    }
}