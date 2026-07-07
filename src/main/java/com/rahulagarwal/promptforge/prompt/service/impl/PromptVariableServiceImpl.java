package com.rahulagarwal.promptforge.prompt.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.prompt.dto.request.CreatePromptVariableRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.UpdatePromptVariableRequest;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptVariableResponse;
import com.rahulagarwal.promptforge.prompt.entity.Prompt;
import com.rahulagarwal.promptforge.prompt.entity.PromptVariable;
import com.rahulagarwal.promptforge.prompt.repository.PromptVariableRepository;
import com.rahulagarwal.promptforge.prompt.service.PromptVariableService;
import com.rahulagarwal.promptforge.security.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PromptVariableServiceImpl implements PromptVariableService {

    private final PromptVariableRepository promptVariableRepository;
    private final AuthorizationService authorizationService;

    @Override
    public PromptVariableResponse create(UUID promptId, CreatePromptVariableRequest request) {
        Prompt prompt = authorizationService.getOwnedPrompt(promptId);
        boolean exists = promptVariableRepository.findByPromptId(promptId).stream().anyMatch(variable -> variable.getVariableName().equalsIgnoreCase(request.variableName()));
        if (exists) {
            throw new BadRequestException("Variable already exists.", ErrorCode.BAD_REQUEST);
        }
        PromptVariable variable = new PromptVariable();
        variable.setPrompt(prompt);
        variable.setVariableName(request.variableName().trim());
        variable.setDescription(request.description());
        variable.setDefaultValue(request.defaultValue());
        variable.setRequired(request.required() == null ? true : request.required());
        PromptVariable saved = promptVariableRepository.save(variable);
        log.info("Variable {} created for prompt {}", saved.getVariableName(), prompt.getTitle());
        return toResponse(saved);
    }

    @Override
    public PromptVariableResponse update(UUID variableId, UpdatePromptVariableRequest request) {
        PromptVariable variable = promptVariableRepository.findById(variableId).orElseThrow(() -> new ResourceNotFoundException("Variable not found.", ErrorCode.RESOURCE_NOT_FOUND));
        authorizationService.getOwnedPrompt(variable.getPrompt().getId());
        if (request.description() != null) {
            variable.setDescription(request.description());
        }
        if (request.defaultValue() != null) {
            variable.setDefaultValue(request.defaultValue());
        }
        if (request.required() != null) {
            variable.setRequired(request.required());
        }
        PromptVariable updated = promptVariableRepository.save(variable);
        log.info("Variable {} updated", updated.getVariableName());
        return toResponse(updated);
    }

    @Override
    public void delete(UUID variableId) {
        PromptVariable variable = promptVariableRepository.findById(variableId).orElseThrow(() -> new ResourceNotFoundException("Variable not found.", ErrorCode.RESOURCE_NOT_FOUND));
        authorizationService.getOwnedPrompt(variable.getPrompt().getId());
        promptVariableRepository.delete(variable);
        log.info("Variable {} deleted", variable.getVariableName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromptVariableResponse> getVariables(UUID promptId) {
        authorizationService.getOwnedPrompt(promptId);
        return promptVariableRepository.findByPromptId(promptId).stream().map(this::toResponse).toList();
    }

    private PromptVariableResponse toResponse(PromptVariable variable) {
        return new PromptVariableResponse(variable.getId(), variable.getVariableName(), variable.getDescription(), variable.getDefaultValue(), variable.getRequired());
    }
}