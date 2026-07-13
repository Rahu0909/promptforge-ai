package com.rahulagarwal.promptforge.codegen.dto.response;

import com.rahulagarwal.promptforge.codegen.enums.CodeGenerationStatus;
import com.rahulagarwal.promptforge.codegen.enums.GenerationType;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record CodeGenerationSummaryResponse(

        UUID id,

        GenerationType generationType,

        String language,

        String framework,

        CodeGenerationStatus status,

        Integer tokenCount,

        Long generationTimeMs,

        OffsetDateTime createdAt

) {
}