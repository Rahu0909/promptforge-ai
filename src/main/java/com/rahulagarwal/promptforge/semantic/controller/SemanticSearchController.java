package com.rahulagarwal.promptforge.semantic.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.semantic.dto.request.SemanticSearchRequest;
import com.rahulagarwal.promptforge.semantic.dto.response.SemanticSearchResponse;
import com.rahulagarwal.promptforge.semantic.service.SemanticSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/semantic")
@RequiredArgsConstructor
public class SemanticSearchController {

    private final SemanticSearchService semanticSearchService;

    @PostMapping("/knowledge")
    public ResponseEntity<ApiResponse<List<SemanticSearchResponse>>> searchKnowledge(@Valid @RequestBody SemanticSearchRequest request) {
        return ResponseEntity.ok(ApiResponse.success(semanticSearchService.searchKnowledge(request)
                , "Knowledge search completed successfully."));
    }
}