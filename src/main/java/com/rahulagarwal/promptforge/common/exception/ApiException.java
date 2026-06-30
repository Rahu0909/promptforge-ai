package com.rahulagarwal.promptforge.common.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus status;

    protected ApiException(
            String message,
            ErrorCode errorCode,
            HttpStatus status
    ) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}