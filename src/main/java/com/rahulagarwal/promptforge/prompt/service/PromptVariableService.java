package com.rahulagarwal.promptforge.prompt.service;

import com.rahulagarwal.promptforge.prompt.dto.request.CreatePromptVariableRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.UpdatePromptVariableRequest;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptVariableResponse;

import java.util.List;
import java.util.UUID;

public interface PromptVariableService {

    PromptVariableResponse create(UUID promptId, CreatePromptVariableRequest request);

    PromptVariableResponse update(UUID variableId, UpdatePromptVariableRequest request);

    void delete(UUID variableId);

    List<PromptVariableResponse> getVariables(UUID promptId);

}