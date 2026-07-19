package com.rahulagarwal.promptforge.rag.retrieval;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RetrievedChunk(

        UUID documentId,

        String documentName,

        Integer chunkIndex,

        String content,

        double similarity

) {}