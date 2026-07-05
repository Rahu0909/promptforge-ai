package com.rahulagarwal.promptforge.user.service;

import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.user.dto.request.UserSearchRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserProfileResponse;
import com.rahulagarwal.promptforge.user.dto.response.UserSummaryResponse;
import com.rahulagarwal.promptforge.user.enums.UserStatus;

import java.util.UUID;

public interface AdminUserService {

    PageResponse<UserSummaryResponse> searchUsers(
            UserSearchRequest request, int page,
            int size,
            String sortBy,
            String direction
    );

    UserProfileResponse getUser(UUID id);

    void updateStatus(UUID id, UserStatus status);

}