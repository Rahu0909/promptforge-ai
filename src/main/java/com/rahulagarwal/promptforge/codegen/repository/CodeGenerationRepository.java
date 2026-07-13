package com.rahulagarwal.promptforge.codegen.repository;

import com.rahulagarwal.promptforge.codegen.entity.CodeGeneration;
import com.rahulagarwal.promptforge.codegen.enums.CodeGenerationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CodeGenerationRepository extends JpaRepository<CodeGeneration, UUID> {

    Page<CodeGeneration> findByProjectIdAndStatus(UUID projectId, CodeGenerationStatus status, Pageable pageable);

}