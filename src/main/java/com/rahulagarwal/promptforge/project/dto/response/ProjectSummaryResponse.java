package com.rahulagarwal.promptforge.project.dto.response;

import com.rahulagarwal.promptforge.project.enums.ProjectStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProjectSummaryResponse(
        UUID id,
        String name,
        ProjectStatus status,
        OffsetDateTime updatedAt
) {
}