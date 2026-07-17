package com.rahulagarwal.promptforge.embedding.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.embedding.dto.request.EmbeddingRequest;
import com.rahulagarwal.promptforge.embedding.dto.response.EmbeddingResponse;
import com.rahulagarwal.promptforge.embedding.service.EmbeddingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/embeddings")
@RequiredArgsConstructor
@Tag(name = "Embeddings", description = "Embedding Generation APIs")
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    @PostMapping
    @Operation(summary = "Generate embedding vector")
    public ResponseEntity<ApiResponse<EmbeddingResponse>> generateEmbedding(@Valid @RequestBody EmbeddingRequest request) {
        EmbeddingResponse response = embeddingService.generateEmbedding(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Embedding generated successfully."));
    }
}