package com.rahulagarwal.promptforge.chat.controller;

import com.rahulagarwal.promptforge.chat.dto.request.SendMessageRequest;
import com.rahulagarwal.promptforge.chat.dto.response.MessageResponse;
import com.rahulagarwal.promptforge.chat.service.MessageService;
import com.rahulagarwal.promptforge.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/conversations/{conversationId}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "Send Message")
    public ResponseEntity<ApiResponse<MessageResponse>> sendMessage(
            @PathVariable UUID conversationId,
            @Valid @RequestBody SendMessageRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        messageService.sendMessage(conversationId, request),
                        "Message sent successfully."
                )
        );
    }

    @GetMapping
    @Operation(summary = "Conversation Messages")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> messages(
            @PathVariable UUID conversationId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        messageService.getConversationMessages(conversationId),
                        "Messages fetched successfully."
                )
        );
    }
}