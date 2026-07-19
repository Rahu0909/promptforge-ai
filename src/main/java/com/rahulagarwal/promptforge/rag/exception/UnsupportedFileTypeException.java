package com.rahulagarwal.promptforge.rag.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UnsupportedFileTypeException extends ApiException {

    public UnsupportedFileTypeException(String extension) {
        super(
                "Unsupported document type: " + extension,
                ErrorCode.UNSUPPORTED_FILE_TYPE,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE
        );
    }

}