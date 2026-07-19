package com.rahulagarwal.promptforge.rag.exception;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class FileStorageException extends ApiException {

    public FileStorageException(String message) {
        super(
                message,
                ErrorCode.FILE_STORAGE_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}