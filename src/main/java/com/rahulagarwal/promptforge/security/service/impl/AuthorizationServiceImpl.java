package com.rahulagarwal.promptforge.security.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.project.repository.ProjectRepository;
import com.rahulagarwal.promptforge.prompt.entity.Prompt;
import com.rahulagarwal.promptforge.prompt.enums.PromptStatus;
import com.rahulagarwal.promptforge.prompt.repository.PromptRepository;
import com.rahulagarwal.promptforge.security.service.AuthorizationService;
import com.rahulagarwal.promptforge.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorizationServiceImpl implements AuthorizationService {

    private final ProjectRepository projectRepository;
    private final PromptRepository promptRepository;

    @Override
    public Project getOwnedProject(UUID projectId) {
        Project project = projectRepository.findByIdAndStatusNot(projectId, ProjectStatus.ARCHIVED).orElseThrow(() -> new ResourceNotFoundException("Project not found.", ErrorCode.RESOURCE_NOT_FOUND));
        UUID currentUserId = SecurityUtils.currentUserId();
        if (!project.getOwner().getAuthUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Project not found.", ErrorCode.RESOURCE_NOT_FOUND);
        }
        return project;
    }

    @Override
    public Prompt getOwnedPrompt(UUID promptId) {
        Prompt prompt = promptRepository.findByIdAndStatusNot(promptId, PromptStatus.ARCHIVED).orElseThrow(() -> new ResourceNotFoundException("Prompt not found.", ErrorCode.RESOURCE_NOT_FOUND));
        UUID currentUserId = SecurityUtils.currentUserId();
        if (!prompt.getProject().getOwner().getAuthUser().getId().equals(currentUserId)) {
            throw new ResourceNotFoundException("Prompt not found.", ErrorCode.RESOURCE_NOT_FOUND);
        }
        return prompt;
    }
}