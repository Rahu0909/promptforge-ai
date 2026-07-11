package com.rahulagarwal.promptforge.ai.provider;

import com.rahulagarwal.promptforge.ai.config.AIProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AIProviderFactory {
    private final List<AIProvider> providers;
    private final AIProperties properties;

    public AIProvider getProvider() {
        return providers.stream()
                .filter(provider ->
                        provider.providerName()
                                .equalsIgnoreCase(properties.getProvider()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No AI Provider configured."));
    }
}