package com.rahulagarwal.promptforge.export.dto.response;

import com.rahulagarwal.promptforge.codegen.enums.CodeGenerationStatus;
import com.rahulagarwal.promptforge.codegen.enums.GenerationType;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record ExportCodeResponse(

        UUID id,

        UUID projectId,

        String prompt,

        GenerationType generationType,

        String language,

        String framework,

        String generatedCode,

        CodeGenerationStatus status,

        String provider,

        String model,

        Integer tokenCount,

        Long generationTimeMs,

        OffsetDateTime createdAt

) {
}