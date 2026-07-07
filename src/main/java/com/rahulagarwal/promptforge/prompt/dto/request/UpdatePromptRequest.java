package com.rahulagarwal.promptforge.prompt.dto.request;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdatePromptRequest(

        UUID categoryId,

        @Size(max = 150)
        String title,

        @Size(max = 1000)
        String description,

        String content

) {
}