package com.rahulagarwal.promptforge.ai.tools.calculator.tool;

import com.rahulagarwal.promptforge.ai.tools.calculator.dto.request.CalculationRequest;
import com.rahulagarwal.promptforge.ai.tools.calculator.dto.response.CalculationResponse;
import com.rahulagarwal.promptforge.ai.tools.calculator.enums.OperationType;
import com.rahulagarwal.promptforge.ai.tools.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalculatorTool {

    private final CalculatorService calculatorService;

    @Tool(name = "calculator", description = """
            Performs mathematical calculations including addition,
            subtraction, multiplication and division.
            Use this tool whenever the user asks to perform arithmetic
            operations or mathematical calculations.
            """)
    public CalculationResponse calculate(
            @ToolParam(description = "The first number") BigDecimal firstNumber,
            @ToolParam(description = "The second number") BigDecimal secondNumber,
            @ToolParam(description = "The mathematical operation") OperationType operation) {
        log.info("""
                #############################################
                CALCULATOR TOOL INVOKED
                firstNumber={}
                secondNumber={}
                operation={}
                #############################################
                """, firstNumber, secondNumber, operation);
        CalculationRequest request = new CalculationRequest(firstNumber, secondNumber, operation);
        CalculationResponse response = calculatorService.calculate(request);
        log.info("Calculator Tool Result = {}", response);
        return response;
    }
}