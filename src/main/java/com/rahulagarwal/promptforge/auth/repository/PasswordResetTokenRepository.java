package com.rahulagarwal.promptforge.auth.repository;

import com.rahulagarwal.promptforge.auth.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    void deleteAllByAuthUserId(UUID userId);

    Optional<PasswordResetToken> findByTokenAndUsedFalse(String token);
}