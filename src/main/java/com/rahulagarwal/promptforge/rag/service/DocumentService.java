package com.rahulagarwal.promptforge.rag.service;

import com.rahulagarwal.promptforge.rag.dto.response.UploadDocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface DocumentService {

    UploadDocumentResponse upload(UUID projectId, MultipartFile file);

    void delete(UUID documentId);
}