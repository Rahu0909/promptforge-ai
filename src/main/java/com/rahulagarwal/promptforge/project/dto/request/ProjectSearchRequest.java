package com.rahulagarwal.promptforge.project.dto.request;

import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.project.enums.ProjectVisibility;

public record ProjectSearchRequest(

        String keyword,

        ProjectStatus status,

        ProjectVisibility visibility

) {
}