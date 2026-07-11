package com.rahulagarwal.promptforge.ai.config;

import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@EnableConfigurationProperties(AIProperties.class)
public class AIConfiguration {

    @Bean
    public SimpleLoggerAdvisor simpleLoggerAdvisor() {
        return new SimpleLoggerAdvisor();
    }

}