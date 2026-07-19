package com.rahulagarwal.promptforge.rag.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class EmptyFileException extends ApiException {

    public EmptyFileException() {
        super(
                "Uploaded file is empty.",
                ErrorCode.EMPTY_FILE,
                HttpStatus.BAD_REQUEST
        );
    }

}