package com.rahulagarwal.promptforge.ai.config;

import com.rahulagarwal.promptforge.ai.tools.calculator.tool.CalculatorTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class ChatClientConfiguration {

    @Bean
    @Primary
    public ChatClient ollamaChatClient(OllamaChatModel chatModel, SimpleLoggerAdvisor loggerAdvisor, MessageChatMemoryAdvisor memoryAdvisor, CalculatorTool calculatorTool) {
        return ChatClient.builder(chatModel).defaultAdvisors(loggerAdvisor, memoryAdvisor).defaultTools(calculatorTool).build();
    }

    @Bean
    public ChatClient geminiChatClient(GoogleGenAiChatModel chatModel, SimpleLoggerAdvisor loggerAdvisor, MessageChatMemoryAdvisor memoryAdvisor, CalculatorTool calculatorTool) {
        return ChatClient.builder(chatModel).defaultAdvisors(loggerAdvisor, memoryAdvisor).defaultTools(calculatorTool).build();
    }

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel chatModel, SimpleLoggerAdvisor loggerAdvisor, MessageChatMemoryAdvisor memoryAdvisor, CalculatorTool calculatorTool) {
        return ChatClient.builder(chatModel).defaultAdvisors(loggerAdvisor, memoryAdvisor).defaultTools(calculatorTool).build();
    }

}