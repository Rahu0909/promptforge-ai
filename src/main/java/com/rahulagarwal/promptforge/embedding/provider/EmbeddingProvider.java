package com.rahulagarwal.promptforge.embedding.provider;

import java.util.List;

public interface EmbeddingProvider {

    float[] generateEmbedding(String text);

    String providerName();

    String modelName();

}