package com.rahulagarwal.promptforge.codegen.service.impl;

import com.rahulagarwal.promptforge.ai.dto.request.ChatRequest;
import com.rahulagarwal.promptforge.ai.factory.AIRequestFactory;
import com.rahulagarwal.promptforge.ai.service.AIService;
import com.rahulagarwal.promptforge.ai.util.PromptTemplateService;
import com.rahulagarwal.promptforge.ai.util.PromptTemplates;
import com.rahulagarwal.promptforge.ai.validation.AIValidationException;
import com.rahulagarwal.promptforge.ai.validation.ValidationResult;
import com.rahulagarwal.promptforge.ai.validation.impl.GeneratedCodeValidator;
import com.rahulagarwal.promptforge.codegen.dto.request.GenerateCodeRequest;
import com.rahulagarwal.promptforge.codegen.dto.response.CodeGenerationResponse;
import com.rahulagarwal.promptforge.codegen.dto.response.CodeGenerationSummaryResponse;
import com.rahulagarwal.promptforge.codegen.dto.structured.GeneratedCodeResponse;
import com.rahulagarwal.promptforge.codegen.entity.CodeGeneration;
import com.rahulagarwal.promptforge.codegen.enums.CodeGenerationStatus;
import com.rahulagarwal.promptforge.codegen.repository.CodeGenerationRepository;
import com.rahulagarwal.promptforge.codegen.service.CodeGenerationService;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeGenerationServiceImpl implements CodeGenerationService {

    private final CodeGenerationRepository codeGenerationRepository;
    private final ProjectRepository projectRepository;
    private final AIRequestFactory aiRequestFactory;
    private final PromptTemplateService promptTemplateService;
    private final AIService aiService;
    private final GeneratedCodeValidator generatedCodeValidator;

    @Override
    public CodeGenerationResponse generateCode(GenerateCodeRequest request) {
        Project project = projectRepository.findById(request.projectId()).orElseThrow(() -> new ResourceNotFoundException("Project not found.", ErrorCode.RESOURCE_NOT_FOUND));
        String prompt = promptTemplateService.buildPrompt(PromptTemplates.STRUCTURED_CODE_GENERATION, Map.of("language", request.language(), "framework", request.framework(), "task", request.prompt()));
        ChatRequest chatRequest = aiRequestFactory.create(prompt);
        long start = System.currentTimeMillis();
        GeneratedCodeResponse aiResponse = aiService.generateStructured(prompt, chatRequest, GeneratedCodeResponse.class);
        ValidationResult validationResult = generatedCodeValidator.validate(aiResponse);
        if (!validationResult.valid()) {
            throw new AIValidationException(validationResult);
        }
        long end = System.currentTimeMillis();
        CodeGeneration generation = new CodeGeneration();
        generation.setProject(project);
        generation.setPrompt(request.prompt());
        generation.setGenerationType(request.generationType());
        generation.setLanguage(request.language());
        generation.setFramework(request.framework());
        generation.setGeneratedCode(aiResponse.code());
        generation.setStatus(CodeGenerationStatus.SUCCESS);
        generation.setProvider("ollama");
        generation.setModel("llama3.2");
        generation.setTokenCount(0);
        generation.setGenerationTimeMs(end - start);
        generation = codeGenerationRepository.save(generation);
        return mapToResponse(generation);
    }

    @Override
    @Transactional(readOnly = true)
    public CodeGenerationResponse getGeneration(UUID generationId) {
        return mapToResponse(getGenerationEntity(generationId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CodeGenerationSummaryResponse> getProjectGenerations(UUID projectId, Pageable pageable) {
        return codeGenerationRepository.findByProjectIdAndStatus(projectId, CodeGenerationStatus.SUCCESS, pageable).map(this::mapToSummary);
    }

    private CodeGeneration getGenerationEntity(UUID generationId) {
        return codeGenerationRepository.findById(generationId).orElseThrow(() -> new ResourceNotFoundException("Code generation not found.", ErrorCode.RESOURCE_NOT_FOUND));
    }

    private CodeGenerationResponse mapToResponse(CodeGeneration entity) {
        return CodeGenerationResponse.builder().id(entity.getId()).projectId(entity.getProject().getId()).prompt(entity.getPrompt()).generationType(entity.getGenerationType()).language(entity.getLanguage()).framework(entity.getFramework()).generatedCode(entity.getGeneratedCode()).status(entity.getStatus()).provider(entity.getProvider()).model(entity.getModel()).tokenCount(entity.getTokenCount()).generationTimeMs(entity.getGenerationTimeMs()).createdAt(entity.getCreatedAt()).build();
    }

    private CodeGenerationSummaryResponse mapToSummary(CodeGeneration entity) {
        return CodeGenerationSummaryResponse.builder().id(entity.getId()).generationType(entity.getGenerationType()).language(entity.getLanguage()).framework(entity.getFramework()).status(entity.getStatus()).tokenCount(entity.getTokenCount()).generationTimeMs(entity.getGenerationTimeMs()).createdAt(entity.getCreatedAt()).build();
    }
}