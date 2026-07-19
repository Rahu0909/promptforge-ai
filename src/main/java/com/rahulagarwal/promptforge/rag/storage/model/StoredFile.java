package com.rahulagarwal.promptforge.rag.storage.model;

import lombok.Builder;

@Builder
public record StoredFile(

        String storagePath,

        String originalFilename,

        String storedFilename,

        String contentType,

        Long size

) {
}