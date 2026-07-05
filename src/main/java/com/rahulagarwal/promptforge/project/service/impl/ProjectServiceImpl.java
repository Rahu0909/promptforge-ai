package com.rahulagarwal.promptforge.project.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.project.dto.request.CreateProjectRequest;
import com.rahulagarwal.promptforge.project.dto.request.ProjectSearchRequest;
import com.rahulagarwal.promptforge.project.dto.request.UpdateProjectRequest;
import com.rahulagarwal.promptforge.project.dto.response.ProjectDashboardResponse;
import com.rahulagarwal.promptforge.project.dto.response.ProjectResponse;
import com.rahulagarwal.promptforge.project.dto.response.ProjectSummaryResponse;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.project.entity.ProjectSettings;
import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.project.mapper.ProjectMapper;
import com.rahulagarwal.promptforge.project.repository.ProjectRepository;
import com.rahulagarwal.promptforge.project.repository.ProjectSettingsRepository;
import com.rahulagarwal.promptforge.project.service.ProjectService;
import com.rahulagarwal.promptforge.project.specification.ProjectSpecification;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import com.rahulagarwal.promptforge.user.service.UserContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectSettingsRepository projectSettingsRepository;
    private final UserContextService userContextService;
    private final ProjectMapper mapper;

    @Override
    public ProjectResponse createProject(CreateProjectRequest request) {
        UserProfile owner = userContextService.getCurrentUserProfile();
        String projectName = request.name().trim();
        if (projectRepository.existsByOwnerAndNameIgnoreCase(owner, projectName)) {
            throw new BadRequestException("Project with the same name already exists.", ErrorCode.PROJECT_ALREADY_EXISTS);
        }
        Project project = new Project();
        project.setOwner(owner);
        project.setName(projectName);
        project.setDescription(request.description() == null ? null : request.description().trim());
        Project savedProject = projectRepository.save(project);
        ProjectSettings settings = createDefaultSettings(savedProject);
        projectSettingsRepository.save(settings);
        log.info("Project '{}' created by {}", savedProject.getName(), owner.getDisplayName());
        return mapper.toResponse(savedProject);
    }

    private ProjectSettings createDefaultSettings(Project project) {
        ProjectSettings settings = new ProjectSettings();
        settings.setProject(project);
        return settings;
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProject(UUID id) {
        UserProfile owner = userContextService.getCurrentUserProfile();
        Project project = projectRepository.findByIdAndOwnerIdAndStatusNot(id, owner.getId(), ProjectStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Project not found.", ErrorCode.PROJECT_NOT_FOUND));
        return mapper.toResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProjectSummaryResponse> searchProjects(ProjectSearchRequest request, int page, int size, String sortBy, String direction) {
        UserProfile owner = userContextService.getCurrentUserProfile();
        Set<String> allowedSortFields = Set.of("name", "createdAt", "updatedAt", "status");
        if (!allowedSortFields.contains(sortBy)) {
            throw new BadRequestException("Invalid sort field.", ErrorCode.BAD_REQUEST);
        }
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Project> projects = projectRepository.findAll(ProjectSpecification.search(owner, request), pageable);
        return new PageResponse<>(projects.getContent().stream().map(mapper::toSummary).toList(), projects.getNumber(), projects.getSize(), projects.getTotalElements(), projects.getTotalPages(), projects.isFirst(), projects.isLast());
    }

    @Override
    public ProjectResponse updateProject(UUID id, UpdateProjectRequest request) {
        UserProfile owner = userContextService.getCurrentUserProfile();
        Project project = projectRepository.findByIdAndOwnerIdAndStatusNot(id, owner.getId(), ProjectStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Project not found.", ErrorCode.PROJECT_NOT_FOUND));
        String newName = request.name().trim();
        if (!project.getName().equalsIgnoreCase(newName) && projectRepository.existsByOwnerAndNameIgnoreCase(owner, newName)) {
            throw new BadRequestException("Project name already exists.", ErrorCode.PROJECT_ALREADY_EXISTS);
        }
        mapper.updateEntity(project, request);
        log.info("Project '{}' updated.", project.getName());
        return mapper.toResponse(project);
    }

    @Override
    public void archiveProject(UUID id) {
        UserProfile owner = userContextService.getCurrentUserProfile();
        Project project = projectRepository.findByIdAndOwnerIdAndStatusNot(id, owner.getId(), ProjectStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Project not found.", ErrorCode.PROJECT_NOT_FOUND));
        project.archive();
        log.info("Project '{}' archived.", project.getName());
    }

    @Override
    public void deleteProject(UUID id) {
        UserProfile owner = userContextService.getCurrentUserProfile();
        Project project = projectRepository.findByIdAndOwnerIdAndStatusNot(id, owner.getId(), ProjectStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Project not found.", ErrorCode.PROJECT_NOT_FOUND));
        project.delete();
        log.info("Project '{}' deleted.", project.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDashboardResponse getDashboard(UUID projectId) {
        UserProfile owner = userContextService.getCurrentUserProfile();
        Project project = projectRepository.findByIdAndOwnerIdAndStatusNot(projectId, owner.getId(), ProjectStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Project not found.", ErrorCode.PROJECT_NOT_FOUND));
        ProjectSettings settings = projectSettingsRepository.findByProjectId(projectId).orElseThrow(() -> new ResourceNotFoundException("Project settings not found.", ErrorCode.RESOURCE_NOT_FOUND));
        return new ProjectDashboardResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                project.getVisibility(),
                settings.getProvider(),
                settings.getModel(),
                settings.getTemperature(),
                settings.getMaxTokens(),
                settings.getStreamingEnabled(),
                settings.getRagEnabled(),
                settings.getMemoryEnabled(),
                0L,
                0L,
                0L,
                0L,
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}