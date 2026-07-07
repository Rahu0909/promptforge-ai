package com.rahulagarwal.promptforge.prompt.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.prompt.dto.request.CreatePromptRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.PromptSearchRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.ToggleFavoriteRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.UpdatePromptRequest;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptResponse;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptSummaryResponse;
import com.rahulagarwal.promptforge.prompt.service.PromptService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prompts")
@RequiredArgsConstructor
@Validated
public class PromptController {

    private final PromptService promptService;

    @PostMapping
    @Operation(summary = "Create Prompt")
    public ResponseEntity<ApiResponse<PromptResponse>> create(@Valid @RequestBody CreatePromptRequest request) {
        return ResponseEntity.ok(ApiResponse.success(promptService.createPrompt(request), "Prompt created successfully."));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Prompt")
    public ResponseEntity<ApiResponse<PromptResponse>> get(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(promptService.getPrompt(id), "Prompt fetched successfully."));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Prompt")
    public ResponseEntity<ApiResponse<PromptResponse>> update(@PathVariable UUID id, @Valid @RequestBody UpdatePromptRequest request) {
        return ResponseEntity.ok(ApiResponse.success(promptService.updatePrompt(id, request), "Prompt updated successfully."));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Archive Prompt")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        promptService.deletePrompt(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Prompt archived successfully."));
    }

    @PatchMapping("/{id}/favorite")
    @Operation(summary = "Toggle Favorite")
    public ResponseEntity<ApiResponse<Void>> favorite(@PathVariable UUID id, @Valid @RequestBody ToggleFavoriteRequest request) {
        promptService.toggleFavorite(id, request.favorite());
        return ResponseEntity.ok(ApiResponse.success(null, "Favorite updated successfully."));
    }

    @GetMapping
    @Operation(summary = "Search Prompts")
    public ResponseEntity<ApiResponse<PageResponse<PromptSummaryResponse>>> search(
            @RequestParam(required = false) UUID projectId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(ApiResponse.success(
                promptService.searchPrompts(
                        new PromptSearchRequest(projectId, categoryId, keyword, status == null ? null : Enum.valueOf(com.rahulagarwal.promptforge.prompt.enums.PromptStatus.class, status.toUpperCase())),
                        page, size, sortBy, direction),
                "Prompts fetched successfully."
        ));
    }
}