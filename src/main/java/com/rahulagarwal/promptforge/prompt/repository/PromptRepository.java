package com.rahulagarwal.promptforge.prompt.repository;

import com.rahulagarwal.promptforge.prompt.entity.Prompt;
import com.rahulagarwal.promptforge.prompt.enums.PromptStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface PromptRepository extends JpaRepository<Prompt, UUID>, JpaSpecificationExecutor<Prompt> {

    boolean existsByProjectIdAndTitleIgnoreCase(UUID projectId, String title);

    Optional<Prompt> findByIdAndStatusNot(UUID id, PromptStatus status);
}