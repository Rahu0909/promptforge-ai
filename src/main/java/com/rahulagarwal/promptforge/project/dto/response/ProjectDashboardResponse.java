package com.rahulagarwal.promptforge.project.dto.response;

import com.rahulagarwal.promptforge.project.enums.AiProvider;
import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.project.enums.ProjectVisibility;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProjectDashboardResponse(
        UUID projectId,
        String projectName,
        String description,
        ProjectStatus status,
        ProjectVisibility visibility,
        AiProvider provider,
        String model,
        Double temperature,
        Integer maxTokens,
        Boolean streamingEnabled,
        Boolean ragEnabled,
        Boolean memoryEnabled,
        long promptCount,
        long chatCount,
        long documentCount,
        long totalTokenUsage,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}