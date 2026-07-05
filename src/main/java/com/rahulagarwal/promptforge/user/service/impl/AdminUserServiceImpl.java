package com.rahulagarwal.promptforge.user.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.user.dto.request.UserSearchRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserProfileResponse;
import com.rahulagarwal.promptforge.user.dto.response.UserSummaryResponse;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import com.rahulagarwal.promptforge.user.enums.UserStatus;
import com.rahulagarwal.promptforge.user.mapper.UserProfileMapper;
import com.rahulagarwal.promptforge.user.repository.UserProfileRepository;
import com.rahulagarwal.promptforge.user.service.AdminUserService;
import com.rahulagarwal.promptforge.user.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    private final UserProfileRepository repository;
    private final UserProfileMapper mapper;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("displayName", "createdAt", "updatedAt", "status");

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserSummaryResponse> searchUsers(UserSearchRequest request, int page, int size, String sortBy, String direction) {
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new BadRequestException("Invalid sort field.", ErrorCode.BAD_REQUEST);
        }
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserProfile> users = repository.findAll(UserSpecification.search(request.keyword(), request.status()), pageable);
        return new PageResponse<>(users.getContent().stream().map(mapper::toSummary).toList(), users.getNumber(), users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isFirst(), users.isLast());

    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getUser(UUID id) {
        UserProfile profile = repository.findByIdAndStatusNot(id, UserStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("User not found.", ErrorCode.USER_NOT_FOUND));
        if (profile.getStatus() == UserStatus.DELETED) {
            throw new ResourceNotFoundException("User not found.", ErrorCode.USER_NOT_FOUND);
        }
        return mapper.toResponse(profile);
    }

    @Override
    public void updateStatus(UUID id, UserStatus status) {
        UserProfile profile = repository.findByIdAndStatusNot(id, UserStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException("User not found.", ErrorCode.USER_NOT_FOUND));
        if (profile.getStatus() == UserStatus.DELETED) {
            throw new BadRequestException("Deleted users cannot be modified.", ErrorCode.BAD_REQUEST);
        }
        profile.updateStatus(status);
        repository.save(profile);
        log.info("Admin updated status of {} to {}", profile.getDisplayName(), status);
    }
}