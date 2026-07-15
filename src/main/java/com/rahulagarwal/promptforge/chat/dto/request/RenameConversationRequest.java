package com.rahulagarwal.promptforge.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RenameConversationRequest(

        @NotBlank
        @Size(max = 150)
        String title

) {
}