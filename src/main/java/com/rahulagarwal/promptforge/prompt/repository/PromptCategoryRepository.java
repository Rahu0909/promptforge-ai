package com.rahulagarwal.promptforge.prompt.repository;

import com.rahulagarwal.promptforge.prompt.entity.PromptCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PromptCategoryRepository extends JpaRepository<PromptCategory, UUID> {

    boolean existsByNameIgnoreCase(String name);

    Optional<PromptCategory> findByNameIgnoreCase(String name);

}