package com.rahulagarwal.promptforge.user.repository;

import com.rahulagarwal.promptforge.user.entity.UserProfile;
import com.rahulagarwal.promptforge.user.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID>, JpaSpecificationExecutor<UserProfile> {

    Optional<UserProfile> findByAuthUserId(UUID authUserId);

    boolean existsByDisplayNameIgnoreCase(String displayName);

    boolean existsByDisplayNameIgnoreCaseAndIdNot(String displayName, UUID id);

    Optional<UserProfile> findByAuthUserIdAndStatusNot(UUID authUserId, UserStatus status);

    Page<UserProfile> findAllByStatusNot(UserStatus status, Pageable pageable);

    Optional<UserProfile> findByIdAndStatusNot(UUID id, UserStatus status);

}