package com.rahulagarwal.promptforge.rag.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class DocumentNotFoundException extends ApiException {

    public DocumentNotFoundException(UUID documentId) {
        super(
                "Document not found with id: " + documentId,
                ErrorCode.DOCUMENT_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

}