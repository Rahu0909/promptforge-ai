package com.rahulagarwal.promptforge.ai.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CalculatorException extends ApiException {

    public CalculatorException(String message) {
        super(message, ErrorCode.VALIDATION_FAILED, HttpStatus.BAD_REQUEST);
    }

}