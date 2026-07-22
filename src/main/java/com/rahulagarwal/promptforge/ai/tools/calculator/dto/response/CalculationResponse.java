package com.rahulagarwal.promptforge.ai.tools.calculator.dto.response;

import java.math.BigDecimal;

public record CalculationResponse(

        BigDecimal result,

        String explanation

) {
}