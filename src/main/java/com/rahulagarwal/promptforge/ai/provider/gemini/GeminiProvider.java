package com.rahulagarwal.promptforge.ai.provider.gemini;

import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import com.rahulagarwal.promptforge.ai.provider.AIProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class GeminiProvider implements AIProvider {

    private final GoogleGenAiChatModel chatModel;
    private final ChatClient geminiChatClient;

    @Override
    public String generate(ChatRequest request, String prompt) {
        return geminiChatClient.prompt().user(prompt).call().content();
    }

    @Override
    public String generateRag(ChatRequest request, String prompt) {
        return geminiChatClient.prompt().system("""
                You are an AI assistant answering questions using ONLY
                the supplied document context.
                
                If the answer is not contained in the context, respond exactly:
                
                I could not find this information in the uploaded documents.
                
                Never fabricate information.
                """).user(prompt).call().content();
    }

    @Override
    public Flux<String> stream(ChatRequest request, String prompt) {
        return geminiChatClient.prompt().user(prompt).stream().content();
    }

    @Override
    public <T> T generateStructured(ChatRequest request, String prompt, Class<T> responseType) {
        return geminiChatClient.prompt().user(prompt).call().entity(responseType);
    }

    @Override
    public String providerName() {
        return "gemini";
    }
}