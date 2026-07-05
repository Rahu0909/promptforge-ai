package com.rahulagarwal.promptforge.project.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.project.dto.request.UpdateProjectSettingsRequest;
import com.rahulagarwal.promptforge.project.dto.response.ProjectSettingsResponse;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.project.entity.ProjectSettings;
import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.project.mapper.ProjectSettingsMapper;
import com.rahulagarwal.promptforge.project.repository.ProjectRepository;
import com.rahulagarwal.promptforge.project.repository.ProjectSettingsRepository;
import com.rahulagarwal.promptforge.project.service.ProjectSettingsService;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import com.rahulagarwal.promptforge.user.service.UserContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProjectSettingsServiceImpl implements ProjectSettingsService {

    private final ProjectRepository projectRepository;
    private final ProjectSettingsRepository projectSettingsRepository;
    private final ProjectSettingsMapper mapper;
    private final UserContextService userContextService;

    @Override
    @Transactional(readOnly = true)
    public ProjectSettingsResponse getSettings(UUID projectId) {
        ProjectSettings settings = getOwnedProjectSettings(projectId);
        return mapper.toResponse(settings);
    }

    @Override
    public ProjectSettingsResponse updateSettings(UUID projectId, UpdateProjectSettingsRequest request) {
        ProjectSettings settings = getOwnedProjectSettings(projectId);
        mapper.updateEntity(settings, request);
        log.info("Project settings updated for project {}", settings.getProject().getName());
        return mapper.toResponse(settings);
    }

    private ProjectSettings getOwnedProjectSettings(UUID projectId) {
        UserProfile owner = userContextService.getCurrentUserProfile();
        Project project = projectRepository.findByIdAndOwnerIdAndStatusNot(projectId, owner.getId(), ProjectStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Project not found.", ErrorCode.PROJECT_NOT_FOUND));
        return projectSettingsRepository.findByProjectId(project.getId()).orElseThrow(() -> new ResourceNotFoundException("Project settings not found.", ErrorCode.RESOURCE_NOT_FOUND));
    }
}