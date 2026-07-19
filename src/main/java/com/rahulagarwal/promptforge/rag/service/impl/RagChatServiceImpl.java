package com.rahulagarwal.promptforge.rag.service.impl;

import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import com.rahulagarwal.promptforge.ai.provider.AIProvider;
import com.rahulagarwal.promptforge.rag.dto.response.CitationResponse;
import com.rahulagarwal.promptforge.rag.dto.response.RagChatResponse;
import com.rahulagarwal.promptforge.rag.prompt.ContextPromptBuilder;
import com.rahulagarwal.promptforge.rag.retrieval.RetrievedChunk;
import com.rahulagarwal.promptforge.rag.retrieval.service.DocumentRetrievalService;
import com.rahulagarwal.promptforge.rag.service.RagChatService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RagChatServiceImpl implements RagChatService {
    private final DocumentRetrievalService retrievalService;
    private final ContextPromptBuilder promptBuilder;
    private final AIProvider aiProvider;

    public RagChatServiceImpl(
            DocumentRetrievalService retrievalService,
            ContextPromptBuilder promptBuilder,
            @Qualifier("ollamaProvider") AIProvider aiProvider) {

        this.retrievalService = retrievalService;
        this.promptBuilder = promptBuilder;
        this.aiProvider = aiProvider;
    }

    @Override
    public RagChatResponse chat(UUID projectId, String question, Integer topK) {
        int effectiveTopK = Objects.requireNonNullElse(topK, 5);
        List<RetrievedChunk> chunks = retrievalService.retrieve(projectId, question, effectiveTopK);
        String prompt = promptBuilder.buildPrompt(question, chunks);
        ChatRequest request = ChatRequest.builder().prompt(prompt).temperature(0.2).topP(0.9).topK(40).maxTokens(1024).lines(20).build();
        String answer = aiProvider.generateRag(request, prompt);
        List<CitationResponse> citations = chunks.stream().map(chunk -> CitationResponse.builder().documentName(chunk.documentName()).chunkIndex(chunk.chunkIndex()).similarity(chunk.similarity()).build()).toList();
        return RagChatResponse.builder().answer(answer).citations(citations).build();
    }
}