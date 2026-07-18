package com.rahulagarwal.promptforge.vector.controller;


import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.vector.dto.request.SaveVectorRequest;
import com.rahulagarwal.promptforge.vector.dto.request.SearchVectorRequest;
import com.rahulagarwal.promptforge.vector.dto.response.SaveVectorResponse;
import com.rahulagarwal.promptforge.vector.dto.response.SearchVectorResponse;
import com.rahulagarwal.promptforge.vector.service.VectorStoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vectors")
@RequiredArgsConstructor
public class VectorController {

    private final VectorStoreService vectorStoreService;

    @PostMapping
    public ApiResponse<SaveVectorResponse> saveEmbedding(@Valid @RequestBody SaveVectorRequest request) {
        SaveVectorResponse response = vectorStoreService.saveEmbedding(request);
        return ApiResponse.success(response, "Embedding stored successfully.");
    }

    @PostMapping("/search")
    public ApiResponse<SearchVectorResponse> search(@Valid @RequestBody SearchVectorRequest request) {
        return ApiResponse.success(vectorStoreService.search(request), "Search completed successfully.");
    }
}