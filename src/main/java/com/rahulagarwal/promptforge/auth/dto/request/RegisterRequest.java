package com.rahulagarwal.promptforge.auth.dto.request;

import com.rahulagarwal.promptforge.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Email is required.")
        @Email(message = "Invalid email format.")
        @Size(max = 150, message = "Email cannot exceed 150 characters.")
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
                message = "Password must contain uppercase, lowercase, digit and special character."
        )
        String password,

        @NotNull(message = "Role is required.")
        UserRole role

) {
}