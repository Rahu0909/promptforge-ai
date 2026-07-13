package com.rahulagarwal.promptforge.ai.validation;

import lombok.Builder;

import java.util.List;

@Builder
public record ValidationResult(

        boolean valid,

        List<String> errors

) {
}