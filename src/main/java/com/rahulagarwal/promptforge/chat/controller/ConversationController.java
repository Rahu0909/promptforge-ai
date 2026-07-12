package com.rahulagarwal.promptforge.chat.controller;

import com.rahulagarwal.promptforge.chat.dto.request.CreateConversationRequest;
import com.rahulagarwal.promptforge.chat.dto.request.RenameConversationRequest;
import com.rahulagarwal.promptforge.chat.dto.response.ConversationResponse;
import com.rahulagarwal.promptforge.chat.service.ConversationService;
import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.common.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    @Operation(summary = "Create Conversation")
    public ResponseEntity<ApiResponse<ConversationResponse>> create(
            @Valid @RequestBody CreateConversationRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        conversationService.create(request),
                        "Conversation created successfully."
                )
        );
    }

    @GetMapping("/{conversationId}")
    @Operation(summary = "Get Conversation")
    public ResponseEntity<ApiResponse<ConversationResponse>> get(
            @PathVariable UUID conversationId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        conversationService.get(conversationId),
                        "Conversation fetched successfully."
                )
        );
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "List Project Conversations")
    public ResponseEntity<ApiResponse<PageResponse<ConversationResponse>>> list(
            @PathVariable UUID projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        conversationService.getProjectConversations(projectId, page, size),
                        "Conversations fetched successfully."
                )
        );
    }

    @PatchMapping("/{conversationId}")
    @Operation(summary = "Rename Conversation")
    public ResponseEntity<ApiResponse<ConversationResponse>> rename(
            @PathVariable UUID conversationId,
            @Valid @RequestBody RenameConversationRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        conversationService.rename(conversationId, request),
                        "Conversation renamed successfully."
                )
        );
    }

    @DeleteMapping("/{conversationId}")
    @Operation(summary = "Archive Conversation")
    public ResponseEntity<ApiResponse<Void>> archive(@PathVariable UUID conversationId) {
        conversationService.archive(conversationId);
        return ResponseEntity.ok(ApiResponse.success(null, "Conversation archived successfully."));
    }
}