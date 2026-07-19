package com.rahulagarwal.promptforge.rag.embedding;

public interface EmbeddingGenerationService {

    float[] generateEmbedding(String text);

}