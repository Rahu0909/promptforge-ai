package com.rahulagarwal.promptforge.user.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.user.dto.request.UpdateUserStatusRequest;
import com.rahulagarwal.promptforge.user.dto.request.UserSearchRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserProfileResponse;
import com.rahulagarwal.promptforge.user.dto.response.UserSummaryResponse;
import com.rahulagarwal.promptforge.user.enums.UserStatus;
import com.rahulagarwal.promptforge.user.service.AdminUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserSummaryResponse>>> getUsers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) UserStatus status

    ) {
        UserSearchRequest request = new UserSearchRequest(keyword, status);
        return ResponseEntity.ok(ApiResponse.success(adminUserService.searchUsers(request, page, size, sortBy, direction), "Users fetched successfully."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(adminUserService.getUser(id), "User fetched successfully."));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateUserStatusRequest request) {
        adminUserService.updateStatus(id, request.status());
        return ResponseEntity.ok(ApiResponse.success(null, "User status updated successfully."));
    }
}