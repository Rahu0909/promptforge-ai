package com.rahulagarwal.promptforge.codegen.dto.request;

import com.rahulagarwal.promptforge.codegen.enums.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public record GenerateCodeRequest(
        @NotNull UUID projectId,
        @NotBlank String prompt,
        @NotNull GenerationType generationType,
        String language,
        String framework
) {}