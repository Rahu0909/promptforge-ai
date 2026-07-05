package com.rahulagarwal.promptforge.user.service;

import com.rahulagarwal.promptforge.user.dto.request.CreateProfileRequest;
import com.rahulagarwal.promptforge.user.dto.request.UpdateAvatarRequest;
import com.rahulagarwal.promptforge.user.dto.request.UpdateProfileRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse createProfile(CreateProfileRequest request);

    UserProfileResponse getMyProfile();

    UserProfileResponse updateProfile(UpdateProfileRequest request);

    UserProfileResponse updateAvatar(UpdateAvatarRequest request);

    void deleteMyProfile();
}