package com.rahulagarwal.promptforge.project.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.project.dto.request.UpdateProjectSettingsRequest;
import com.rahulagarwal.promptforge.project.dto.response.ProjectSettingsResponse;
import com.rahulagarwal.promptforge.project.service.ProjectSettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/settings")
@RequiredArgsConstructor
public class ProjectSettingsController {

    private final ProjectSettingsService projectSettingsService;

    @GetMapping
    public ResponseEntity<ApiResponse<ProjectSettingsResponse>> getSettings(@PathVariable UUID projectId) {
        return ResponseEntity.ok(ApiResponse.success(projectSettingsService.getSettings(projectId), "Project settings fetched successfully."));

    }

    @PatchMapping
    public ResponseEntity<ApiResponse<ProjectSettingsResponse>> updateSettings(@PathVariable UUID projectId, @Valid @RequestBody UpdateProjectSettingsRequest request) {
        return ResponseEntity.ok(ApiResponse.success(projectSettingsService.updateSettings(projectId, request), "Project settings updated successfully."));
    }

}