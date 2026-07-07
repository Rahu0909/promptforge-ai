package com.rahulagarwal.promptforge.prompt.repository;

import com.rahulagarwal.promptforge.prompt.entity.PromptVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromptVersionRepository extends JpaRepository<PromptVersion, UUID> {

    List<PromptVersion> findByPromptIdOrderByVersionNumberDesc(UUID promptId);

    Optional<PromptVersion> findByPromptIdAndVersionNumber(UUID promptId, Integer versionNumber);

}