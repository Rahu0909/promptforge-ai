package com.rahulagarwal.promptforge.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LogoutAllRequest(

        @Email
        @NotBlank
        String email

) {
}