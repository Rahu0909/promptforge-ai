package com.rahulagarwal.promptforge.ai.provider.ollama;

import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import com.rahulagarwal.promptforge.ai.provider.AIProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class OllamaProvider implements AIProvider {

    private final ChatClient ollamaChatClient;

    @Override
    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String generate(ChatRequest request, String prompt) {

        OllamaChatOptions options = OllamaChatOptions.builder()
                .temperature(request.temperature())
                .topP(request.topP())
                .topK(request.topK())
                .numPredict(request.maxTokens())
                .build();

        log.info("""
                        ===============================
                        OLLAMA REQUEST STARTED
                        ===============================
                        Provider      : {}
                        Conversation  : {}
                        Temperature   : {}
                        TopP          : {}
                        TopK          : {}
                        MaxTokens     : {}
                        ===============================
                        """,
                providerName(),
                request.conversationId(),
                request.temperature(),
                request.topP(),
                request.topK(),
                request.maxTokens());

        log.info("Prompt being sent to Ollama:\n{}", prompt);

        String response = ollamaChatClient
                .prompt()
                .advisors(advisor ->
                        advisor.param(
                                ChatMemory.CONVERSATION_ID,
                                request.conversationId()
                        ))
                .options(options)
                .user(prompt)
                .call()
                .content();

        log.info("""
                ===============================
                RAW RESPONSE FROM OLLAMA
                {}
                ===============================
                """, response);

        return response;
    }

    @Override
    public Flux<String> stream(ChatRequest request, String prompt) {
        OllamaChatOptions options = OllamaChatOptions.builder().temperature(request.temperature()).topP(request.topP()).topK(request.topK()).numPredict(request.maxTokens()).build();
        return ollamaChatClient
                .prompt()
                .advisors(advisor ->
                        advisor.param(
                                ChatMemory.CONVERSATION_ID,
                                request.conversationId()
                        ))
                .options(options).user(prompt).stream().content();
    }

    @Override
    public <T> T generateStructured(ChatRequest request, String prompt, Class<T> responseType) {
        OllamaChatOptions options = OllamaChatOptions.builder().temperature(request.temperature()).topP(request.topP()).topK(request.topK()).numPredict(request.maxTokens()).build();
        return ollamaChatClient
                .prompt()
                .advisors(advisor ->
                        advisor.param(
                                ChatMemory.CONVERSATION_ID,
                                request.conversationId()
                        ))
                .options(options).system("""
                        You are a JSON API.
                        Return ONLY valid JSON.
                        Never output markdown.
                        Never explain anything.
                        Never output text outside the JSON object.
                        """).user(prompt).call().entity(responseType);
    }

    @Override
    public String generateRag(ChatRequest request, String prompt) {
        OllamaChatOptions options = OllamaChatOptions.builder().temperature(request.temperature()).topP(request.topP()).topK(request.topK()).numPredict(request.maxTokens()).build();
        return ollamaChatClient
                .prompt()
                .advisors(advisor ->
                        advisor.param(
                                ChatMemory.CONVERSATION_ID,
                                request.conversationId()
                        ))
                .options(options).system("""
                        You are an AI assistant answering questions using only
                        the supplied document context.
                        
                        If the answer is not present in the supplied context,
                        respond exactly:
                        
                        I could not find this information in the uploaded documents.
                        
                        Never fabricate information.
                        """).user(prompt).call().content();
    }

    @Override
    public String providerName() {
        return "ollama";
    }
}