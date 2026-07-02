package com.rahulagarwal.promptforge.auth.repository;

import com.rahulagarwal.promptforge.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByAuthUserId(UUID userId);

    void deleteAllByAuthUserId(UUID userId);

}