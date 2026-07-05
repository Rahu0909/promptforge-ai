package com.rahulagarwal.promptforge.project.dto.response;

import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.project.enums.ProjectVisibility;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String name,
        String description,
        ProjectStatus status,
        ProjectVisibility visibility,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}