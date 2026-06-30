package com.rahulagarwal.promptforge.common.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {

    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);
    }
}