package com.rahulagarwal.promptforge.ai.service.impl;

import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import com.rahulagarwal.promptforge.ai.dto.response.ChatResponse;
import com.rahulagarwal.promptforge.ai.dto.structured.InterviewQuestionResponse;
import com.rahulagarwal.promptforge.ai.provider.AIProvider;
import com.rahulagarwal.promptforge.ai.provider.AIProviderFactory;
import com.rahulagarwal.promptforge.ai.service.AIService;
import com.rahulagarwal.promptforge.ai.util.PromptTemplateService;
import com.rahulagarwal.promptforge.ai.util.PromptTemplates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {
    private final AIProviderFactory providerFactory;
    private final PromptTemplateService promptTemplateService;

    @Override
    public ChatResponse chat(ChatRequest request) {
        AIProvider provider = providerFactory.getProvider();
        String finalPrompt = promptTemplateService.buildPrompt(PromptTemplates.EXPLAIN_TOPIC, Map.of("topic", request.prompt(), "lines", request.lines()));
        log.info("Final Prompt Sent To LLM:\n{}", finalPrompt);
        String response = provider.generate(request, finalPrompt);
        return new ChatResponse(response);
    }

    @Override
    public Flux<String> stream(ChatRequest request) {
        AIProvider provider = providerFactory.getProvider();
        String prompt = promptTemplateService.buildPrompt(PromptTemplates.EXPLAIN_TOPIC, Map.of("topic", request.prompt(), "lines", request.lines()));
        return provider.stream(request, prompt);
    }

    @Override
    public InterviewQuestionResponse generateInterviewQuestion(ChatRequest request) {
        AIProvider provider = providerFactory.getProvider();
        String prompt = promptTemplateService.buildPrompt(PromptTemplates.INTERVIEW_QUESTION, Map.of("topic", request.prompt()));
        return provider.generateStructured(request, prompt, InterviewQuestionResponse.class);
    }
}