package com.rahulagarwal.promptforge.rag.controller;

import com.rahulagarwal.promptforge.rag.dto.request.RagChatRequest;
import com.rahulagarwal.promptforge.rag.dto.response.RagChatResponse;
import com.rahulagarwal.promptforge.rag.service.RagChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects/{projectId}/rag")
@Tag(name = "RAG Chat")
public class RagChatController {

    private final RagChatService ragChatService;

    @PostMapping("/chat")
    @Operation(summary = "Ask questions over uploaded documents")
    public ResponseEntity<RagChatResponse> chat(@PathVariable UUID projectId, @Valid @RequestBody RagChatRequest request) {
        return ResponseEntity.ok(ragChatService.chat(projectId, request.question(), request.topK()));
    }
}