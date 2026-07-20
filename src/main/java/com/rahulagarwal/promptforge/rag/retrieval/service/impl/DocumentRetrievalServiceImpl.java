package com.rahulagarwal.promptforge.rag.retrieval.service.impl;

import com.rahulagarwal.promptforge.rag.embedding.EmbeddingGenerationService;
import com.rahulagarwal.promptforge.rag.repository.DocumentChunkRepository;
import com.rahulagarwal.promptforge.rag.retrieval.RetrievedChunk;
import com.rahulagarwal.promptforge.rag.retrieval.projection.ChunkSearchResult;
import com.rahulagarwal.promptforge.rag.retrieval.service.DocumentRetrievalService;
import com.rahulagarwal.promptforge.rag.retrieval.util.VectorFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentRetrievalServiceImpl implements DocumentRetrievalService {
    private final EmbeddingGenerationService embeddingGenerationService;
    private final DocumentChunkRepository chunkRepository;

    @Override
    public List<RetrievedChunk> retrieve(UUID projectId, String question, int topK) {
        log.info("Generating embedding for user query...");
        float[] embedding = embeddingGenerationService.generateEmbedding(question);
        String vector = VectorFormatter.format(embedding);
        List<ChunkSearchResult> results = chunkRepository.searchSimilarChunks(projectId, vector, topK);
        log.info("Retrieved {} similar chunks.", results.size());
        return results.stream()
                .map(result -> RetrievedChunk.builder()
                        .documentId(result.getDocumentId())
                        .documentName(result.getDocumentName())
                        .chunkIndex(result.getChunkIndex())
                        .content(result.getContent())
                        .similarity(result.getSimilarity())
                        .build())
                .toList();
    }

    @Override
    public List<RetrievedChunk> retrieve(UUID projectId, String question, Double minimumSimilarity, int topK) {
        log.info("Generating embedding for semantic search...");
        float[] embedding = embeddingGenerationService.generateEmbedding(question);
        String vector = VectorFormatter.format(embedding);
        List<ChunkSearchResult> results = chunkRepository.searchSimilarChunks(projectId, vector, minimumSimilarity, topK);
        log.info("Retrieved {} semantic chunks.", results.size());
        return results.stream().map(this::mapChunk).toList();
    }

    private RetrievedChunk mapChunk(ChunkSearchResult result) {
        return RetrievedChunk.builder()
                .documentId(result.getDocumentId())
                .documentName(result.getDocumentName())
                .chunkIndex(result.getChunkIndex())
                .content(result.getContent())
                .similarity(result.getSimilarity())
                .build();
    }
}