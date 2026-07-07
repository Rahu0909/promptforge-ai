package com.rahulagarwal.promptforge.prompt.repository;

import com.rahulagarwal.promptforge.prompt.entity.PromptVariable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PromptVariableRepository extends JpaRepository<PromptVariable, UUID> {

    List<PromptVariable> findByPromptId(UUID promptId);

}