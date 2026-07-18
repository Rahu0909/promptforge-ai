package com.rahulagarwal.promptforge.vector.repository.projection;

import java.util.UUID;

public interface VectorSearchProjection {

    UUID getId();

    UUID getSourceId();

    String getContent();

    Double getSimilarity();

}