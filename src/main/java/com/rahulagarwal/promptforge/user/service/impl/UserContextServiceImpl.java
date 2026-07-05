package com.rahulagarwal.promptforge.user.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.security.util.SecurityUtils;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import com.rahulagarwal.promptforge.user.enums.UserStatus;
import com.rahulagarwal.promptforge.user.repository.UserProfileRepository;
import com.rahulagarwal.promptforge.user.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserContextServiceImpl implements UserContextService {

    private final UserProfileRepository repository;

    @Override
    public UserProfile getCurrentUserProfile() {

        return repository.findByAuthUserIdAndStatusNot(SecurityUtils.currentUserId(), UserStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("Profile not found.", ErrorCode.RESOURCE_NOT_FOUND));

    }

}