package com.rahulagarwal.promptforge.codegen.service;

import com.rahulagarwal.promptforge.codegen.dto.request.GenerateCodeRequest;
import com.rahulagarwal.promptforge.codegen.dto.response.CodeGenerationResponse;
import com.rahulagarwal.promptforge.codegen.dto.response.CodeGenerationSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CodeGenerationService {

    CodeGenerationResponse generateCode(GenerateCodeRequest request);

    CodeGenerationResponse getGeneration(UUID generationId);

    Page<CodeGenerationSummaryResponse> getProjectGenerations(UUID projectId, Pageable pageable);
}