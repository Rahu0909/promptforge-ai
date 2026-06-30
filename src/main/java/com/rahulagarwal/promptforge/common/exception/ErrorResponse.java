package com.rahulagarwal.promptforge.common.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(

        boolean success,

        String message,

        ErrorCode errorCode,

        int status,

        String error,

        List<ValidationError> errors,

        LocalDateTime timestamp,

        String path

) {
}