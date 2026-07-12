package com.rahulagarwal.promptforge.ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "promptforge.ai")
public class AIProperties {

    /**
     * ollama | openai | gemini
     */
    private String provider;

    /**
     * Default model configured for the selected provider.
     */
    private String model;

    /**
     * Default generation parameters.
     */
    private Double temperature;

    private Double topP;

    private Integer topK;

    private Integer maxTokens;

}