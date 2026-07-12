package com.rahulagarwal.promptforge.chat.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RenameConversationRequest(

        @NotBlank
        String title

) {
}