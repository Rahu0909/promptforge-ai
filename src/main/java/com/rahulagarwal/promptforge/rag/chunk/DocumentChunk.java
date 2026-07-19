package com.rahulagarwal.promptforge.rag.chunk;

import lombok.Builder;

@Builder
public record DocumentChunk(

        Integer chunkIndex,

        String content,

        Integer tokenCount

) {
}