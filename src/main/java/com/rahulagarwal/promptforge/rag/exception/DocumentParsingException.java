package com.rahulagarwal.promptforge.rag.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class DocumentParsingException extends ApiException {

    public DocumentParsingException(String message) {
        super(
                message,
                ErrorCode.DOCUMENT_PARSING_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}