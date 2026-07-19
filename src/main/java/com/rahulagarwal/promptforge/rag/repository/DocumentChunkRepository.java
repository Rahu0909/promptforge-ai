package com.rahulagarwal.promptforge.rag.repository;

import com.rahulagarwal.promptforge.rag.entity.DocumentChunk;
import com.rahulagarwal.promptforge.rag.retrieval.projection.ChunkSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, UUID> {

    @Query(value = """
            SELECT
                kd.id AS documentId,
                kd.document_name AS documentName,
                dc.chunk_index AS chunkIndex,
                dc.content AS content,
                1 - (dc.embedding <=> CAST(:embedding AS vector)) AS similarity
            FROM document_chunks dc
            INNER JOIN knowledge_documents kd
                    ON dc.document_id = kd.id
            WHERE kd.project_id = :projectId
              AND kd.deleted = false
            ORDER BY dc.embedding <=> CAST(:embedding AS vector)
            LIMIT :topK
            """, nativeQuery = true)
    List<ChunkSearchResult> searchSimilarChunks(

            @Param("projectId") UUID projectId,

            @Param("embedding") String embedding,

            @Param("topK") int topK);

}