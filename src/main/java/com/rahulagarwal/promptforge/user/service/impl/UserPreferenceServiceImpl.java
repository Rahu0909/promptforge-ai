package com.rahulagarwal.promptforge.user.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.security.util.SecurityUtils;
import com.rahulagarwal.promptforge.user.dto.request.UpdateUserPreferenceRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserPreferenceResponse;
import com.rahulagarwal.promptforge.user.entity.UserPreference;
import com.rahulagarwal.promptforge.user.enums.UserStatus;
import com.rahulagarwal.promptforge.user.mapper.UserPreferenceMapper;
import com.rahulagarwal.promptforge.user.repository.UserPreferenceRepository;
import com.rahulagarwal.promptforge.user.repository.UserProfileRepository;
import com.rahulagarwal.promptforge.user.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserPreferenceMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public UserPreferenceResponse getMyPreferences() {
        var profile = userProfileRepository.findByAuthUserIdAndStatusNot(SecurityUtils.currentUserId(), UserStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Profile not found.", ErrorCode.RESOURCE_NOT_FOUND));
        UserPreference preference = userPreferenceRepository.findByUserProfileId(profile.getId()).orElseThrow(() -> new ResourceNotFoundException("Preferences not found.", ErrorCode.RESOURCE_NOT_FOUND));
        return mapper.toResponse(preference);
    }

    @Override
    public UserPreferenceResponse updatePreferences(UpdateUserPreferenceRequest request) {
        var profile = userProfileRepository.findByAuthUserIdAndStatusNot(SecurityUtils.currentUserId(), UserStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Profile not found.", ErrorCode.RESOURCE_NOT_FOUND));
        UserPreference preference = userPreferenceRepository.findByUserProfileId(profile.getId()).orElseThrow(() -> new ResourceNotFoundException("Preferences not found.", ErrorCode.RESOURCE_NOT_FOUND));
        mapper.update(preference, request);
        UserPreference updated = userPreferenceRepository.save(preference);
        log.info("Preferences updated for {}", profile.getDisplayName());
        return mapper.toResponse(updated);
    }
}