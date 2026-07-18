package com.rahulagarwal.promptforge.vector.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.embedding.provider.EmbeddingProvider;
import com.rahulagarwal.promptforge.embedding.provider.EmbeddingProviderFactory;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.project.repository.ProjectRepository;
import com.rahulagarwal.promptforge.vector.dto.request.SaveVectorRequest;
import com.rahulagarwal.promptforge.vector.dto.response.SaveVectorResponse;
import com.rahulagarwal.promptforge.vector.entity.DocumentEmbedding;
import com.rahulagarwal.promptforge.vector.repository.DocumentEmbeddingRepository;
import com.rahulagarwal.promptforge.vector.service.VectorStoreService;
import com.rahulagarwal.promptforge.vector.dto.request.SearchVectorRequest;
import com.rahulagarwal.promptforge.vector.dto.response.SearchResult;
import com.rahulagarwal.promptforge.vector.dto.response.SearchVectorResponse;
import com.rahulagarwal.promptforge.vector.repository.projection.VectorSearchProjection;
import com.rahulagarwal.promptforge.vector.util.VectorUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorStoreServiceImpl implements VectorStoreService {

    private final DocumentEmbeddingRepository repository;
    private final ProjectRepository projectRepository;
    private final EmbeddingProviderFactory embeddingProviderFactory;

    @Override
    @Transactional
    public SaveVectorResponse saveEmbedding(SaveVectorRequest request) {

        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project",
                                ErrorCode.RESOURCE_NOT_FOUND
                        ));
        EmbeddingProvider provider = embeddingProviderFactory.getProvider();
        float[] embedding = provider.generateEmbedding(request.content());
        DocumentEmbedding documentEmbedding = DocumentEmbedding.builder()
                .project(project)
                .sourceType(request.sourceType())
                .sourceId(request.sourceId())
                .content(request.content())
                .embedding(embedding)
                .metadata(request.metadata())
                .build();
        DocumentEmbedding saved = repository.save(documentEmbedding);
        return SaveVectorResponse.builder()
                .embeddingId(saved.getId())
                .dimensions(embedding.length)
                .message("Embedding stored successfully.")
                .build();
    }

    @Override
    @Transactional
    public SearchVectorResponse search(SearchVectorRequest request) {

        float[] embedding = embeddingProviderFactory
                .getProvider()
                .generateEmbedding(request.query());

        String pgVector = VectorUtils.toPgVector(embedding);

        List<VectorSearchProjection> matches =
                repository.searchSimilar(
                        request.projectId(),
                        pgVector,
                        request.topK()
                );

        List<SearchResult> results = matches.stream()
                .map(match -> SearchResult.builder()
                        .embeddingId(match.getId())
                        .sourceId(match.getSourceId())
                        .content(match.getContent())
                        .similarity(match.getSimilarity())
                        .build())
                .toList();

        return SearchVectorResponse.builder()
                .totalMatches(results.size())
                .results(results)
                .build();
    }
}