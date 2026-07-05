package com.rahulagarwal.promptforge.project.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.project.dto.request.CreateProjectRequest;
import com.rahulagarwal.promptforge.project.dto.request.ProjectSearchRequest;
import com.rahulagarwal.promptforge.project.dto.request.UpdateProjectRequest;
import com.rahulagarwal.promptforge.project.dto.response.ProjectResponse;
import com.rahulagarwal.promptforge.project.dto.response.ProjectSummaryResponse;
import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.project.enums.ProjectVisibility;
import com.rahulagarwal.promptforge.project.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @RequestBody CreateProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(projectService.createProject(request), "Project created successfully."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProject(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(projectService.getProject(id), "Project fetched successfully."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(@PathVariable UUID id, @Valid @RequestBody UpdateProjectRequest request) {
        return ResponseEntity.ok(ApiResponse.success(projectService.updateProject(id, request), "Project updated successfully."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProjectSummaryResponse>>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) ProjectVisibility visibility,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        ProjectSearchRequest request = new ProjectSearchRequest(keyword, status, visibility);
        return ResponseEntity.ok(ApiResponse.success(projectService.searchProjects(request, page, size, sortBy, direction), "Projects fetched successfully."));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<ApiResponse<Void>> archiveProject(@PathVariable UUID id) {
        projectService.archiveProject(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Project archived successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Project deleted successfully."));
    }
}