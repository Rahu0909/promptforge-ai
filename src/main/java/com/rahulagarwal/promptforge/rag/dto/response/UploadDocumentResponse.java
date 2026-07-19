package com.rahulagarwal.promptforge.rag.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UploadDocumentResponse(

        UUID documentId,

        String documentName,

        String status

) {
}