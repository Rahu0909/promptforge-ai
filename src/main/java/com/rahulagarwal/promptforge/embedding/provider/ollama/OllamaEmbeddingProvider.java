package com.rahulagarwal.promptforge.embedding.provider.ollama;

import com.rahulagarwal.promptforge.ai.config.AIProperties;
import com.rahulagarwal.promptforge.embedding.provider.EmbeddingProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OllamaEmbeddingProvider implements EmbeddingProvider {

    private final EmbeddingModel embeddingModel;
    private final AIProperties aiProperties;

    public OllamaEmbeddingProvider(
            @Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel,
            AIProperties aiProperties) {

        this.embeddingModel = embeddingModel;
        this.aiProperties = aiProperties;
    }

    @Override
    public float[] generateEmbedding(String text) {
        log.info("Generating embedding using Ollama");
        return embeddingModel.embed(text);
    }

    @Override
    public String providerName() {
        return "ollama";
    }

    @Override
    public String modelName() {
        return aiProperties.getModel();
    }
}