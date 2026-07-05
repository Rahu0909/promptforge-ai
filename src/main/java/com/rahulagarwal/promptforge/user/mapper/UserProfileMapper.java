package com.rahulagarwal.promptforge.user.mapper;

import com.rahulagarwal.promptforge.user.dto.request.UpdateProfileRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserProfileResponse;
import com.rahulagarwal.promptforge.user.dto.response.UserSummaryResponse;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileResponse toResponse(UserProfile profile) {

        return new UserProfileResponse(
                profile.getId(),
                profile.getAuthUser().getId(),
                profile.getAuthUser().getEmail(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getDisplayName(),
                profile.getBio(),
                profile.getAvatarUrl(),
                profile.getTimezone(),
                profile.getLanguage(),
                profile.getCreatedAt()
        );
    }

    public void updateEntity(
            UserProfile profile,
            UpdateProfileRequest request
    ) {

        if (request.firstName() != null) {
            profile.setFirstName(request.firstName().trim());
        }
        if (request.lastName() != null) {
            profile.setLastName(request.lastName().trim());
        }
        if (request.displayName() != null) {
            profile.setDisplayName(request.displayName().trim());
        }
        if (request.bio() != null) {
            profile.setBio(
                    request.bio().isBlank()
                            ? null
                            : request.bio().trim()
            );
        }
        if (request.avatarUrl() != null) {
            profile.setAvatarUrl(
                    request.avatarUrl().isBlank()
                            ? null
                            : request.avatarUrl().trim()
            );
        }
        if (request.timezone() != null) {
            profile.setTimezone(request.timezone().trim());
        }
        if (request.language() != null) {
            profile.setLanguage(request.language().trim());
        }
    }

    public UserSummaryResponse toSummary(
            UserProfile profile
    ){
        return new UserSummaryResponse(
                profile.getId(),
                profile.getDisplayName(),
                profile.getAuthUser().getEmail(),
                profile.getStatus(),
                profile.getCreatedAt()

        );
    }
}