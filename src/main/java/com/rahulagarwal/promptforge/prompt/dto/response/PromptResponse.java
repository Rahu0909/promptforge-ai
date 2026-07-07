package com.rahulagarwal.promptforge.prompt.dto.response;

import com.rahulagarwal.promptforge.prompt.enums.PromptStatus;
import com.rahulagarwal.promptforge.prompt.enums.PromptVisibility;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PromptResponse(

        UUID id,

        UUID projectId,

        String projectName,

        UUID categoryId,

        String categoryName,

        String title,

        String description,

        String content,

        PromptStatus status,

        PromptVisibility visibility,

        Boolean favorite,

        Integer latestVersion,

        OffsetDateTime createdAt,

        OffsetDateTime updatedAt

) {
}