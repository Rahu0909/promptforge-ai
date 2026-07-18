package com.rahulagarwal.promptforge.vector.repository;

import com.rahulagarwal.promptforge.vector.entity.DocumentEmbedding;
import com.rahulagarwal.promptforge.vector.repository.projection.VectorSearchProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DocumentEmbeddingRepository extends JpaRepository<DocumentEmbedding, UUID> {

    @Query(value = """
            SELECT
                id,
                source_id AS sourceId,
                content,
                (1 - (embedding <=> CAST(:embedding AS vector))) AS similarity
            FROM document_embeddings
            WHERE project_id = :projectId
            ORDER BY embedding <=> CAST(:embedding AS vector)
            LIMIT :topK
            """, nativeQuery = true)
    List<VectorSearchProjection> searchSimilar(
            @Param("projectId") UUID projectId,
            @Param("embedding") String embedding,
            @Param("topK") int topK
    );

}