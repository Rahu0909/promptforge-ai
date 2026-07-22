package com.rahulagarwal.promptforge.ai.tools.calculator.service;

import com.rahulagarwal.promptforge.ai.tools.calculator.dto.request.CalculationRequest;
import com.rahulagarwal.promptforge.ai.tools.calculator.dto.response.CalculationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public CalculationResponse calculate(CalculationRequest request) {
        log.info("""
                ===== CalculatorService =====
                Operation : {}
                First     : {}
                Second    : {}
                """, request.operation(), request.firstNumber(), request.secondNumber());
        BigDecimal result = request.operation().calculate(request.firstNumber(), request.secondNumber());
        log.info("Calculated Result = {}", result);
        CalculationResponse response = new CalculationResponse(result, buildExplanation(request, result));
        log.info("Returning {}", response);
        return response;
    }

    private String buildExplanation(CalculationRequest request, BigDecimal result) {
        return String.format("%s %s %s = %s", request.firstNumber(), request.operation().getSymbol(), request.secondNumber(), result);
    }

}