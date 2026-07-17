package com.rahulagarwal.promptforge.embedding.provider;

import com.rahulagarwal.promptforge.ai.config.AIProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmbeddingProviderFactory {

    private final List<EmbeddingProvider> providers;

    private final AIProperties properties;

    public EmbeddingProvider getProvider() {

        return providers.stream()
                .filter(provider ->
                        provider.providerName()
                                .equalsIgnoreCase(properties.getProvider()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No Embedding Provider configured."));
    }

}