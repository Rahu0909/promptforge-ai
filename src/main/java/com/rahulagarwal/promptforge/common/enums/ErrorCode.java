package com.rahulagarwal.promptforge.common.enums;

public enum ErrorCode {

    // Common Errors
    INTERNAL_SERVER_ERROR,
    VALIDATION_FAILED,
    BAD_REQUEST,
    RESOURCE_NOT_FOUND,

    // Authentication
    INVALID_CREDENTIALS,
    INVALID_TOKEN,
    TOKEN_EXPIRED,
    ACCESS_DENIED,
    UNAUTHORIZED,

    // User
    USER_NOT_FOUND,
    USER_ALREADY_EXISTS,

    // Project
    PROJECT_NOT_FOUND,
    PROJECT_ALREADY_EXISTS,

    // Prompt
    INVALID_PROMPT,
    PROMPT_TOO_LARGE,

    // AI
    AI_PROVIDER_ERROR,
    AI_RESPONSE_ERROR,

    // RAG
    VECTOR_SEARCH_FAILED
}