package com.rahulagarwal.promptforge.rag.chunk;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "rag.chunking")
public class ChunkingProperties {

    /**
     * Maximum characters per chunk.
     */
    private Integer chunkSize = 1000;

    /**
     * Characters repeated in next chunk.
     */
    private Integer overlap = 200;

}