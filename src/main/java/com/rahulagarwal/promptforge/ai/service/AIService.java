package com.rahulagarwal.promptforge.ai.service;

import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import com.rahulagarwal.promptforge.ai.dto.response.ChatResponse;
import com.rahulagarwal.promptforge.ai.dto.structured.InterviewQuestionResponse;
import reactor.core.publisher.Flux;

public interface AIService {

    ChatResponse chat(ChatRequest request);

    Flux<String> stream(ChatRequest request);

    InterviewQuestionResponse generateInterviewQuestion(ChatRequest request);
}