package com.rahulagarwal.promptforge.project.service;

import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.project.dto.request.CreateProjectRequest;
import com.rahulagarwal.promptforge.project.dto.request.ProjectSearchRequest;
import com.rahulagarwal.promptforge.project.dto.request.UpdateProjectRequest;
import com.rahulagarwal.promptforge.project.dto.response.ProjectResponse;
import com.rahulagarwal.promptforge.project.dto.response.ProjectSummaryResponse;

import java.util.UUID;

public interface ProjectService {

    ProjectResponse createProject(CreateProjectRequest request);

    ProjectResponse getProject(UUID id);

    ProjectResponse updateProject(UUID id, UpdateProjectRequest request);

    PageResponse<ProjectSummaryResponse> searchProjects(ProjectSearchRequest request, int page, int size, String sortBy, String direction);

    void archiveProject(UUID id);

    void deleteProject(UUID id);

}