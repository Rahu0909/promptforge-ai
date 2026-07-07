package com.rahulagarwal.promptforge.prompt.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.prompt.dto.request.CreatePromptVariableRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.UpdatePromptVariableRequest;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptVariableResponse;
import com.rahulagarwal.promptforge.prompt.service.PromptVariableService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PromptVariableController {

    private final PromptVariableService promptVariableService;

    @PostMapping("/api/v1/prompts/{promptId}/variables")
    @Operation(summary = "Create Prompt Variable")
    public ResponseEntity<ApiResponse<PromptVariableResponse>> create(@PathVariable UUID promptId, @Valid @RequestBody CreatePromptVariableRequest request) {
        return ResponseEntity.ok(ApiResponse.success(promptVariableService.create(promptId, request), "Variable created successfully."));
    }

    @GetMapping("/api/v1/prompts/{promptId}/variables")
    @Operation(summary = "Get Prompt Variables")
    public ResponseEntity<ApiResponse<List<PromptVariableResponse>>> getVariables(@PathVariable UUID promptId) {
        return ResponseEntity.ok(ApiResponse.success(promptVariableService.getVariables(promptId), "Variables fetched successfully."));
    }

    @PatchMapping("/api/v1/variables/{variableId}")
    @Operation(summary = "Update Prompt Variable")
    public ResponseEntity<ApiResponse<PromptVariableResponse>> update(@PathVariable UUID variableId, @Valid @RequestBody UpdatePromptVariableRequest request) {
        return ResponseEntity.ok(ApiResponse.success(promptVariableService.update(variableId, request), "Variable updated successfully."));
    }

    @DeleteMapping("/api/v1/variables/{variableId}")
    @Operation(summary = "Delete Prompt Variable")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID variableId) {
        promptVariableService.delete(variableId);
        return ResponseEntity.ok(ApiResponse.success(null, "Variable deleted successfully."));
    }
}