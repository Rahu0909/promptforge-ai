package com.rahulagarwal.promptforge.user.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.user.dto.request.UpdateUserPreferenceRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserPreferenceResponse;
import com.rahulagarwal.promptforge.user.service.UserPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserPreferenceResponse>> getPreferences() {
        return ResponseEntity.ok(ApiResponse.success(userPreferenceService.getMyPreferences(), "Preferences fetched successfully."));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<UserPreferenceResponse>> updatePreferences(@Valid @RequestBody UpdateUserPreferenceRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userPreferenceService.updatePreferences(request), "Preferences updated successfully."));
    }
}