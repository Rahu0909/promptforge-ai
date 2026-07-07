package com.rahulagarwal.promptforge.prompt.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptResponse;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptVersionResponse;
import com.rahulagarwal.promptforge.prompt.service.PromptVersionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prompts/{promptId}/versions")
@RequiredArgsConstructor
public class PromptVersionController {

    private final PromptVersionService promptVersionService;

    @GetMapping
    @Operation(summary = "List Prompt Versions")
    public ResponseEntity<ApiResponse<List<PromptVersionResponse>>> getVersions(@PathVariable UUID promptId) {
        return ResponseEntity.ok(ApiResponse.success(promptVersionService.getVersions(promptId), "Prompt versions fetched successfully."));
    }

    @GetMapping("/{version}")
    @Operation(summary = "Get Prompt Version")
    public ResponseEntity<ApiResponse<PromptVersionResponse>> getVersion(@PathVariable UUID promptId, @PathVariable Integer version) {
        return ResponseEntity.ok(ApiResponse.success(promptVersionService.getVersion(promptId, version), "Prompt version fetched successfully."));
    }

    @PostMapping("/{version}/restore")
    @Operation(summary = "Restore Prompt Version")
    public ResponseEntity<ApiResponse<PromptResponse>> restoreVersion(@PathVariable UUID promptId, @PathVariable Integer version) {
        return ResponseEntity.ok(ApiResponse.success(promptVersionService.restoreVersion(promptId, version), "Prompt restored successfully."));
    }
}