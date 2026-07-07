package com.rahulagarwal.promptforge.prompt.dto.request;

import com.rahulagarwal.promptforge.prompt.enums.PromptStatus;

import java.util.UUID;

public record PromptSearchRequest(

        UUID projectId,

        UUID categoryId,

        String keyword,

        PromptStatus status

) {
}