package com.rahulagarwal.promptforge.semantic.service;

import com.rahulagarwal.promptforge.semantic.dto.request.SemanticSearchRequest;
import com.rahulagarwal.promptforge.semantic.dto.response.SemanticSearchResponse;

import java.util.List;

public interface SemanticSearchService {

    List<SemanticSearchResponse> searchKnowledge(SemanticSearchRequest request);


}