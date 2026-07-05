package com.rahulagarwal.promptforge.project.dto.request;

import com.rahulagarwal.promptforge.project.enums.ProjectVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProjectRequest(

        @NotBlank(message = "Project name is required.")
        @Size(max = 150)
        String name,

        @Size(max = 2000)
        String description,

        ProjectVisibility visibility

) {
}