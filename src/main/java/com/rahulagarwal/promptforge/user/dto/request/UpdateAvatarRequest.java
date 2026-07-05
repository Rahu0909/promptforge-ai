package com.rahulagarwal.promptforge.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateAvatarRequest(

        @NotBlank(message = "Avatar URL is required.")
        @Size(max = 500)
        @Pattern(
                regexp = "^(https?://).+",
                message = "Avatar URL must start with http:// or https://"
        )
        String avatarUrl

) {
}