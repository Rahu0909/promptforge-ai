package com.rahulagarwal.promptforge.ai.provider.ollama;

import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import com.rahulagarwal.promptforge.ai.provider.AIProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class OllamaProvider implements AIProvider {

    private final OllamaChatModel chatModel;
    private final SimpleLoggerAdvisor simpleLoggerAdvisor;

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public String generate(ChatRequest request, String prompt) {

        ChatClient client = ChatClient.builder(chatModel)
                .defaultAdvisors(simpleLoggerAdvisor)
                .build();

        OllamaChatOptions options = OllamaChatOptions.builder()
                .temperature(request.temperature())
                .topP(request.topP())
                .topK(request.topK())
                .numPredict(request.maxTokens())
                .build();

        log.info("""
                Provider      : {}
                Temperature   : {}
                TopP          : {}
                TopK          : {}
                MaxTokens     : {}
                """,
                providerName(),
                request.temperature(),
                request.topP(),
                request.topK(),
                request.maxTokens());

        return client.prompt()
                .options(options)
                .user(prompt)
                .call()
                .content();
    }

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public Flux<String> stream(ChatRequest request, String prompt) {

        ChatClient client = ChatClient.builder(chatModel)
                .defaultAdvisors(simpleLoggerAdvisor)
                .build();

        OllamaChatOptions options = OllamaChatOptions.builder()
                .temperature(request.temperature())
                .topP(request.topP())
                .topK(request.topK())
                .numPredict(request.maxTokens())
                .build();

        return client.prompt()
                .options(options)
                .user(prompt)
                .stream()
                .content();
    }

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public <T> T generateStructured(
            ChatRequest request,
            String prompt,
            Class<T> responseType) {

        ChatClient client = ChatClient.builder(chatModel)
                .defaultAdvisors(simpleLoggerAdvisor)
                .build();

        OllamaChatOptions options = OllamaChatOptions.builder()
                .temperature(request.temperature())
                .topP(request.topP())
                .topK(request.topK())
                .numPredict(request.maxTokens())
                .build();

        return client.prompt()
                .options(options)
                .user(prompt)
                .call()
                .entity(responseType);
    }

    @Override
    public String providerName() {
        return "ollama";
    }

}