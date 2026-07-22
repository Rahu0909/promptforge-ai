package com.rahulagarwal.promptforge.ai.tools.calculator.service;

import com.rahulagarwal.promptforge.ai.tools.calculator.dto.request.CalculationRequest;
import com.rahulagarwal.promptforge.ai.tools.calculator.dto.response.CalculationResponse;

public interface CalculatorService {

    CalculationResponse calculate(CalculationRequest request);

}