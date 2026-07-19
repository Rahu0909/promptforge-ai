package com.rahulagarwal.promptforge.rag.embedding;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OllamaEmbeddingGenerationService
        implements EmbeddingGenerationService {

    private final EmbeddingModel embeddingModel;

    public OllamaEmbeddingGenerationService(
            @Qualifier("ollamaEmbeddingModel")
            EmbeddingModel embeddingModel) {

        this.embeddingModel = embeddingModel;
    }

    @Override
    public float[] generateEmbedding(String text) {
        return embeddingModel.embed(text);
    }
}