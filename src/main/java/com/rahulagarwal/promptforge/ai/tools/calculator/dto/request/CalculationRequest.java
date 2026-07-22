package com.rahulagarwal.promptforge.ai.tools.calculator.dto.request;

import com.rahulagarwal.promptforge.ai.tools.calculator.enums.OperationType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalculationRequest(

        @NotNull(message = "First number is required.")
        BigDecimal firstNumber,

        @NotNull(message = "Second number is required.")
        BigDecimal secondNumber,

        @NotNull(message = "Operation type is required.")
        OperationType operation

) {
}