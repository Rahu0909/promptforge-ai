package com.rahulagarwal.promptforge.project.mapper;

import com.rahulagarwal.promptforge.project.dto.request.UpdateProjectSettingsRequest;
import com.rahulagarwal.promptforge.project.dto.response.ProjectSettingsResponse;
import com.rahulagarwal.promptforge.project.entity.ProjectSettings;
import org.springframework.stereotype.Component;

@Component
public class ProjectSettingsMapper {

    public ProjectSettingsResponse toResponse(ProjectSettings settings) {

        return new ProjectSettingsResponse(
                settings.getProvider(),
                settings.getModel(),
                settings.getTemperature(),
                settings.getMaxTokens(),
                settings.getStreamingEnabled(),
                settings.getRagEnabled(),
                settings.getMemoryEnabled()
        );
    }

    public void updateEntity(ProjectSettings settings, UpdateProjectSettingsRequest request) {
        if (request.provider() != null) {
            settings.setProvider(request.provider());
        }

        if (request.model() != null) {
            settings.setModel(request.model().trim());
        }

        if (request.temperature() != null) {
            settings.setTemperature(request.temperature());
        }

        if (request.maxTokens() != null) {
            settings.setMaxTokens(request.maxTokens());
        }

        if (request.streamingEnabled() != null) {
            settings.setStreamingEnabled(request.streamingEnabled());
        }

        if (request.ragEnabled() != null) {
            settings.setRagEnabled(request.ragEnabled());
        }

        if (request.memoryEnabled() != null) {
            settings.setMemoryEnabled(request.memoryEnabled());
        }

    }

}