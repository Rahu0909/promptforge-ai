package com.rahulagarwal.promptforge.prompt.mapper;

import com.rahulagarwal.promptforge.prompt.dto.response.PromptResponse;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptSummaryResponse;
import com.rahulagarwal.promptforge.prompt.entity.Prompt;
import org.springframework.stereotype.Component;

@Component
public class PromptMapper {

    public PromptResponse toResponse(Prompt prompt) {

        return new PromptResponse(
                prompt.getId(),
                prompt.getProject().getId(),
                prompt.getProject().getName(),
                prompt.getCategory().getId(),
                prompt.getCategory().getName(),
                prompt.getTitle(),
                prompt.getDescription(),
                prompt.getContent(),
                prompt.getStatus(),
                prompt.getVisibility(),
                prompt.getFavorite(),
                prompt.getLatestVersion(),
                prompt.getCreatedAt(),
                prompt.getUpdatedAt());
    }

    public PromptSummaryResponse toSummary(Prompt prompt) {
        return new PromptSummaryResponse(
                prompt.getId(),
                prompt.getTitle(),
                prompt.getCategory().getName(),
                prompt.getStatus(), prompt.getFavorite(), prompt.getLatestVersion());
    }
}