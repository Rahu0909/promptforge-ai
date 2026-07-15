package com.rahulagarwal.promptforge.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateConversationRequest(

        @NotNull
        UUID projectId,

        @NotBlank
        @Size(max = 150)
        String title

) {
}