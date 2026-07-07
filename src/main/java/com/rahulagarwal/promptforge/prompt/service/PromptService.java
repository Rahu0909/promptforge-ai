package com.rahulagarwal.promptforge.prompt.service;

import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.prompt.dto.request.CreatePromptRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.PromptSearchRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.UpdatePromptRequest;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptResponse;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptSummaryResponse;

import java.util.UUID;

public interface PromptService {

    PromptResponse createPrompt(CreatePromptRequest request);

    PromptResponse getPrompt(UUID id);

    PromptResponse updatePrompt(UUID id, UpdatePromptRequest request);

    void deletePrompt(UUID id);

    void toggleFavorite(UUID id, Boolean favorite);

    PageResponse<PromptSummaryResponse> searchPrompts(PromptSearchRequest request, int page, int size, String sortBy, String direction);

}