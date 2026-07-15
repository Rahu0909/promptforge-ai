package com.rahulagarwal.promptforge.chat.repository;

import com.rahulagarwal.promptforge.chat.entity.Conversation;
import com.rahulagarwal.promptforge.chat.enums.ConversationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    @EntityGraph(attributePaths = "project")
    Optional<Conversation> findByIdAndStatus(UUID id, ConversationStatus status);

    @EntityGraph(attributePaths = "project")
    Page<Conversation> findByProjectIdAndStatus(UUID projectId, ConversationStatus status, Pageable pageable);

    Page<Conversation> findByProjectIdAndStatusAndTitleContainingIgnoreCase(UUID projectId, ConversationStatus status, String keyword, Pageable pageable);
}