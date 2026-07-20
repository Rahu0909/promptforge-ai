package com.rahulagarwal.promptforge.semantic.service.impl;

import com.rahulagarwal.promptforge.rag.retrieval.service.DocumentRetrievalService;
import com.rahulagarwal.promptforge.semantic.dto.request.SemanticSearchRequest;
import com.rahulagarwal.promptforge.semantic.dto.response.SemanticSearchResponse;
import com.rahulagarwal.promptforge.semantic.mapper.SemanticSearchMapper;
import com.rahulagarwal.promptforge.semantic.service.SemanticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SemanticSearchServiceImpl implements SemanticSearchService {

    private final DocumentRetrievalService retrievalService;
    private final SemanticSearchMapper mapper;

    @Override
    public List<SemanticSearchResponse> searchKnowledge(SemanticSearchRequest request) {
        log.info("Executing semantic knowledge search for project={} query={}", request.projectId(), request.query());
        return retrievalService.retrieve(request.projectId(),
                request.query(), request.minimumSimilarity(),
                request.limit()).stream().map(mapper::toResponse).toList();
    }

}