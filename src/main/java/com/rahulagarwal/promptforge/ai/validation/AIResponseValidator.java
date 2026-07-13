package com.rahulagarwal.promptforge.ai.validation;

public interface AIResponseValidator<T> {

    ValidationResult validate(T response);

}