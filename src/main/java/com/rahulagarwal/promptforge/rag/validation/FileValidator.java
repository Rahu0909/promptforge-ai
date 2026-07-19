package com.rahulagarwal.promptforge.rag.validation;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidator {

    void validate(MultipartFile file);

}