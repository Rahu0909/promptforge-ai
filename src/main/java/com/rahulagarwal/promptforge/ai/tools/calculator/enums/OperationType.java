package com.rahulagarwal.promptforge.ai.tools.calculator.enums;

import com.rahulagarwal.promptforge.ai.exception.CalculatorException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@RequiredArgsConstructor
public enum OperationType {

    ADD("+", "Addition") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal second) {
            return first.add(second);
        }
    },

    SUBTRACT("-", "Subtraction") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal second) {
            return first.subtract(second);
        }
    },

    MULTIPLY("×", "Multiplication") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal second) {
            return first.multiply(second);
        }
    },

    DIVIDE("÷", "Division") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal second) {
            if (BigDecimal.ZERO.compareTo(second) == 0) {
                throw new CalculatorException("Division by zero is not allowed.");
            }
            return first.divide(second, 6, RoundingMode.HALF_UP);
        }
    };

    private final String symbol;
    private final String displayName;
    public abstract BigDecimal calculate(BigDecimal first, BigDecimal second);
}