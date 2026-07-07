package com.rahulagarwal.promptforge.prompt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreatePromptRequest(

        @NotNull
        UUID projectId,

        @NotNull
        UUID categoryId,

        @NotBlank
        @Size(max = 150)
        String title,

        @Size(max = 1000)
        String description,

        @NotBlank
        String content

) {
}