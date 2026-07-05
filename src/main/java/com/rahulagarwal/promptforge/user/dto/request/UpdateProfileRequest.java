package com.rahulagarwal.promptforge.user.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(

        @Size(max = 60)
        String firstName,

        @Size(max = 60)
        String lastName,

        @Size(max = 120)
        String displayName,

        @Size(max = 500)
        String bio,

        @Size(max = 500)
        String avatarUrl,

        @Size(max = 80)
        String timezone,

        @Size(max = 20)
        String language

) {
}