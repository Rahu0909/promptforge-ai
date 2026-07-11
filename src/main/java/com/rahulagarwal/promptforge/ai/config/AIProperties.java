package com.rahulagarwal.promptforge.ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.ai")
public class AIProperties {

    private String provider = "ollama";

}