package com.rahulagarwal.promptforge.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel chatModel, SimpleLoggerAdvisor loggerAdvisor, MessageChatMemoryAdvisor memoryAdvisor) {
        return ChatClient.builder(chatModel).defaultAdvisors(loggerAdvisor, memoryAdvisor).build();

    }

}