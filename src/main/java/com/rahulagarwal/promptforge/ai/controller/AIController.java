package com.rahulagarwal.promptforge.ai.controller;

import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import com.rahulagarwal.promptforge.ai.dto.response.ChatResponse;
import com.rahulagarwal.promptforge.ai.dto.structured.InterviewQuestionResponse;
import com.rahulagarwal.promptforge.ai.service.AIService;
import com.rahulagarwal.promptforge.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping("/chat")
    @Operation(summary = "Simple AI Chat")
    public ResponseEntity<ApiResponse<ChatResponse>> chat(
            @Valid @RequestBody ChatRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        aiService.chat(request),
                        "Response generated successfully."
                )
        );
    }

    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@Valid @RequestBody ChatRequest request) {
        return aiService.stream(request);
    }

    @PostMapping("/interview")
    public ResponseEntity<ApiResponse<InterviewQuestionResponse>> generateInterviewQuestion(@RequestBody @Valid ChatRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                aiService.generateInterviewQuestion(request), "Interview question generated."));
    }
}