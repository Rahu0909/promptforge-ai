package com.rahulagarwal.promptforge.user.controller;

import com.rahulagarwal.promptforge.common.response.ApiResponse;
import com.rahulagarwal.promptforge.user.dto.request.CreateProfileRequest;
import com.rahulagarwal.promptforge.user.dto.request.UpdateAvatarRequest;
import com.rahulagarwal.promptforge.user.dto.request.UpdateProfileRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserProfileResponse;
import com.rahulagarwal.promptforge.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserProfileResponse>> createProfile(@Valid @RequestBody CreateProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(userProfileService.createProfile(request), "Profile created successfully."));

    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile() {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.getMyProfile(), "Profile fetched successfully."));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.updateProfile(request), "Profile updated successfully."));
    }

    @PatchMapping("/avatar")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateAvatar(@Valid @RequestBody UpdateAvatarRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.updateAvatar(request), "Avatar updated successfully."));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMyProfile() {
        userProfileService.deleteMyProfile();
        return ResponseEntity.noContent().build();

    }
}