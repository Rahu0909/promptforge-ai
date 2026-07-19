package com.rahulagarwal.promptforge.rag.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UploadDocumentRequest(

        @NotNull
        UUID projectId

) {
}