package com.rahulagarwal.promptforge.auth.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn,
        String role,
        String email
) {
}