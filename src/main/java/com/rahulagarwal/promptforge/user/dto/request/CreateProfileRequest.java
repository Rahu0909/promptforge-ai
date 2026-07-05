package com.rahulagarwal.promptforge.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProfileRequest(

        @NotBlank(message = "First name is required.")
        @Size(max = 60)
        String firstName,

        @NotBlank(message = "Last name is required.")
        @Size(max = 60)
        String lastName,

        @NotBlank(message = "Display name is required.")
        @Size(max = 120)
        String displayName,

        @Size(max = 500)
        String bio,

        @Size(max = 80)
        String timezone,

        @Size(max = 20)
        String language

) {
}