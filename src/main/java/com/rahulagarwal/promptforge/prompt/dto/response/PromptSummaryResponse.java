package com.rahulagarwal.promptforge.prompt.dto.response;

import com.rahulagarwal.promptforge.prompt.enums.PromptStatus;

import java.util.UUID;

public record PromptSummaryResponse(

        UUID id,

        String title,

        String category,

        PromptStatus status,

        Boolean favorite,

        Integer latestVersion

) {
}