package com.rahulagarwal.promptforge.rag.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class FileValidationException extends ApiException {

    public FileValidationException(String message) {
        super(
                message,
                ErrorCode.FILE_VALIDATION_FAILED,
                HttpStatus.BAD_REQUEST
        );
    }

}