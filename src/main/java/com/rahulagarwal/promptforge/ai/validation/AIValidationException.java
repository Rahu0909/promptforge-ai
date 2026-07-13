package com.rahulagarwal.promptforge.ai.validation;

import lombok.Getter;

@Getter
public class AIValidationException extends RuntimeException {

    private final ValidationResult validationResult;

    public AIValidationException(ValidationResult validationResult) {
        super("AI response validation failed.");
        this.validationResult = validationResult;
    }
}