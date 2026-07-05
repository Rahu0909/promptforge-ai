package com.rahulagarwal.promptforge.project.service;

import com.rahulagarwal.promptforge.project.dto.request.UpdateProjectSettingsRequest;
import com.rahulagarwal.promptforge.project.dto.response.ProjectSettingsResponse;

import java.util.UUID;

public interface ProjectSettingsService {

    ProjectSettingsResponse getSettings(UUID projectId);

    ProjectSettingsResponse updateSettings(
            UUID projectId,
            UpdateProjectSettingsRequest request
    );

}