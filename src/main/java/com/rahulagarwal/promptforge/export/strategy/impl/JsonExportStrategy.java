package com.rahulagarwal.promptforge.export.strategy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahulagarwal.promptforge.codegen.entity.CodeGeneration;
import com.rahulagarwal.promptforge.export.dto.response.ExportCodeResponse;
import com.rahulagarwal.promptforge.export.enums.ExportType;
import com.rahulagarwal.promptforge.export.strategy.ExportStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonExportStrategy implements ExportStrategy {

    private final ObjectMapper objectMapper;

    @Override
    public ExportType getType() {
        return ExportType.JSON;
    }

    @Override
    public ByteArrayResource export(CodeGeneration generation) {
        ExportCodeResponse response = ExportCodeResponse.builder()
                .id(generation.getId())
                .projectId(generation.getProject().getId())
                .prompt(generation.getPrompt())
                .language(generation.getLanguage())
                .framework(generation.getFramework())
                .generatedCode(generation.getGeneratedCode())
                .provider(generation.getProvider())
                .model(generation.getModel())
                .generationType(generation.getGenerationType())
                .status(generation.getStatus())
                .tokenCount(generation.getTokenCount())
                .generationTimeMs(generation.getGenerationTimeMs())
                .createdAt(generation.getCreatedAt())
                .build();
        try {
            byte[] bytes = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsBytes(response);
            return new ByteArrayResource(bytes);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to export JSON.", ex);
        }
    }

    @Override
    public String getFileName(CodeGeneration generation) {
        return "generation-" + generation.getId() + ".json";
    }

    @Override
    public String getContentType() {
        return "application/json";
    }
}