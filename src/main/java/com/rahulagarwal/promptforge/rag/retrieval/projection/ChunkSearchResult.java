package com.rahulagarwal.promptforge.rag.retrieval.projection;

import java.util.UUID;

public interface ChunkSearchResult {

    UUID getDocumentId();

    String getDocumentName();

    Integer getChunkIndex();

    String getContent();

    Double getSimilarity();

}