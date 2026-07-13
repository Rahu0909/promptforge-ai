package com.rahulagarwal.promptforge.codegen.validator;

import com.rahulagarwal.promptforge.codegen.dto.request.GenerateCodeRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CodeGenerationValidator {

    public void validate(GenerateCodeRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        if (request.projectId() == null) {
            throw new IllegalArgumentException("Project Id is required.");
        }

        if (!StringUtils.hasText(request.prompt())) {
            throw new IllegalArgumentException("Prompt cannot be blank.");
        }

        if (request.generationType() == null) {
            throw new IllegalArgumentException("Generation Type is required.");
        }
    }

}