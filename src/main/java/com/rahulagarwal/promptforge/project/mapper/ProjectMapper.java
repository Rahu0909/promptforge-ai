package com.rahulagarwal.promptforge.project.mapper;

import com.rahulagarwal.promptforge.project.dto.request.UpdateProjectRequest;
import com.rahulagarwal.promptforge.project.dto.response.ProjectResponse;
import com.rahulagarwal.promptforge.project.dto.response.ProjectSummaryResponse;
import com.rahulagarwal.promptforge.project.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectResponse toResponse(Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                project.getVisibility(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );

    }

    public ProjectSummaryResponse toSummary(Project project) {

        return new ProjectSummaryResponse(
                project.getId(),
                project.getName(),
                project.getStatus(),
                project.getUpdatedAt()
        );

    }

    public void updateEntity(
            Project project,
            UpdateProjectRequest request
    ) {

        project.setName(request.name().trim());

        project.setDescription(
                request.description() == null
                        ? null
                        : request.description().trim()
        );

        if (request.visibility() != null) {
            project.setVisibility(request.visibility());
        }

    }

}