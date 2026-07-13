package com.rahulagarwal.promptforge.codegen.controller;

import com.rahulagarwal.promptforge.codegen.dto.request.GenerateCodeRequest;
import com.rahulagarwal.promptforge.codegen.dto.response.CodeGenerationResponse;
import com.rahulagarwal.promptforge.codegen.dto.response.CodeGenerationSummaryResponse;
import com.rahulagarwal.promptforge.codegen.service.CodeGenerationService;
import com.rahulagarwal.promptforge.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/code-generations")
public class CodeGenerationController {

    private final CodeGenerationService codeGenerationService;

    @PostMapping
    @Operation(summary = "Generate source code using AI")
    public ResponseEntity<ApiResponse<CodeGenerationResponse>> generateCode(@Valid @RequestBody GenerateCodeRequest request) {
        return ResponseEntity.ok(ApiResponse.success(codeGenerationService.generateCode(request), "Code generated successfully."));
    }

    @GetMapping("/{generationId}")
    @Operation(summary = "Get code generation by id")
    public ResponseEntity<ApiResponse<CodeGenerationResponse>> getGeneration(@PathVariable UUID generationId) {
        return ResponseEntity.ok(ApiResponse.success(codeGenerationService.getGeneration(generationId), "Generation ftched successfully."));
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get project code generations")
    public ResponseEntity<ApiResponse<Page<CodeGenerationSummaryResponse>>> getProjectGenerations(@PathVariable UUID projectId, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(codeGenerationService.getProjectGenerations(projectId, pageable), "Generations fetched successfully."));
    }
}