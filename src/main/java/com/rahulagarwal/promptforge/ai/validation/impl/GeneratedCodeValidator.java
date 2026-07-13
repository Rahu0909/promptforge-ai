package com.rahulagarwal.promptforge.ai.validation.impl;

import com.rahulagarwal.promptforge.ai.validation.AIResponseValidator;
import com.rahulagarwal.promptforge.ai.validation.ValidationResult;
import com.rahulagarwal.promptforge.codegen.dto.structured.GeneratedCodeResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GeneratedCodeValidator implements AIResponseValidator<GeneratedCodeResponse> {

    @Override
    public ValidationResult validate(GeneratedCodeResponse response) {
        List<String> errors = new ArrayList<>();
        if (response == null) {
            errors.add("AI response is null.");
        } else {
            if (isBlank(response.title())) errors.add("Missing title.");
            if (isBlank(response.language())) errors.add("Missing language.");
            if (isBlank(response.framework())) errors.add("Missing framework.");
            if (isBlank(response.code())) errors.add("Generated code is empty.");
            else if (response.code().length() < 30) errors.add("Generated code is too short.");
        }
        return ValidationResult.builder().valid(errors.isEmpty()).errors(errors).build();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}