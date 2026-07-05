package com.rahulagarwal.promptforge.user.service.impl;

import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.auth.repository.AuthUserRepository;
import com.rahulagarwal.promptforge.auth.service.RefreshTokenService;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.security.util.SecurityUtils;
import com.rahulagarwal.promptforge.user.dto.request.CreateProfileRequest;
import com.rahulagarwal.promptforge.user.dto.request.UpdateAvatarRequest;
import com.rahulagarwal.promptforge.user.dto.request.UpdateProfileRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserProfileResponse;
import com.rahulagarwal.promptforge.user.entity.UserPreference;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import com.rahulagarwal.promptforge.user.enums.UserStatus;
import com.rahulagarwal.promptforge.user.mapper.UserProfileMapper;
import com.rahulagarwal.promptforge.user.repository.UserPreferenceRepository;
import com.rahulagarwal.promptforge.user.repository.UserProfileRepository;
import com.rahulagarwal.promptforge.user.service.UserContextService;
import com.rahulagarwal.promptforge.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final AuthUserRepository authUserRepository;
    private final UserProfileMapper mapper;
    private final UserPreferenceRepository userPreferenceRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserContextService userContextService;

    @Override
    public UserProfileResponse createProfile(CreateProfileRequest request) {
        if (userProfileRepository.findByAuthUserIdAndStatusNot(SecurityUtils.currentUserId(), UserStatus.DELETED).isPresent()) {
            throw new BadRequestException("Profile already exists.", ErrorCode.BAD_REQUEST);
        }
        AuthUser authUser = authUserRepository.findById(SecurityUtils.currentUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found.", ErrorCode.USER_NOT_FOUND));
        String displayName = request.displayName().trim();
        if (userProfileRepository.existsByDisplayNameIgnoreCase(displayName)) {
            throw new BadRequestException("Display name already exists.", ErrorCode.BAD_REQUEST);
        }
        UserProfile profile = buildProfile(request, authUser);
        profile.setDisplayName(displayName);
        UserProfile savedProfile = userProfileRepository.save(profile);
        UserPreference preference = new UserPreference();
        preference.setUserProfile(savedProfile);
        log.debug("Default preferences created for {}", authUser.getEmail());
        userPreferenceRepository.save(preference);
        log.info("Profile created for {}", authUser.getEmail());
        return mapper.toResponse(savedProfile);

    }

    private UserProfile buildProfile(CreateProfileRequest request, AuthUser authUser) {
        UserProfile profile = new UserProfile();
        profile.setAuthUser(authUser);
        profile.setFirstName(request.firstName().trim());
        profile.setLastName(request.lastName().trim());
        profile.setDisplayName(request.displayName().trim());
        profile.setBio(request.bio() == null || request.bio().isBlank() ? null : request.bio().trim());
        profile.setTimezone(request.timezone() == null || request.timezone().isBlank() ? "UTC" : request.timezone().trim());
        profile.setLanguage(request.language() == null || request.language().isBlank() ? "en" : request.language().trim());
        return profile;
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile() {
        UserProfile profile = userProfileRepository.findByAuthUserIdAndStatusNot(SecurityUtils.currentUserId(), UserStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Profile not found.", ErrorCode.RESOURCE_NOT_FOUND));
        return mapper.toResponse(profile);
    }

    @Override
    public UserProfileResponse updateProfile(UpdateProfileRequest request) {
        UserProfile profile = userProfileRepository.findByAuthUserIdAndStatusNot(SecurityUtils.currentUserId(), UserStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Profile not found.", ErrorCode.RESOURCE_NOT_FOUND));
        if (request.displayName() != null) {
            String displayName = request.displayName().trim();
            if (userProfileRepository.existsByDisplayNameIgnoreCaseAndIdNot(displayName, profile.getId())) {
                throw new BadRequestException("Display name already exists.", ErrorCode.BAD_REQUEST);
            }
        }
        mapper.updateEntity(profile, request);
        UserProfile updated = userProfileRepository.save(profile);
        log.info("Profile updated for {}", profile.getAuthUser().getEmail());
        return mapper.toResponse(updated);
    }

    @Override
    public UserProfileResponse updateAvatar(UpdateAvatarRequest request) {
        UserProfile profile = userProfileRepository.findByAuthUserIdAndStatusNot(SecurityUtils.currentUserId(), UserStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Profile not found.", ErrorCode.RESOURCE_NOT_FOUND));
        profile.setAvatarUrl(request.avatarUrl().trim());
        UserProfile updated = userProfileRepository.save(profile);
        log.info("Avatar updated for {}", profile.getAuthUser().getEmail());
        return mapper.toResponse(updated);
    }

    @Override
    public void deleteMyProfile() {
        UserProfile profile = userContextService.getCurrentUserProfile();
        profile.delete();
        userProfileRepository.save(profile);
        refreshTokenService.revokeAll(profile.getAuthUser());
        log.info("Profile soft deleted for {}", profile.getAuthUser().getEmail());
    }
}