package com.rahulagarwal.promptforge.rag.repository;

import com.rahulagarwal.promptforge.rag.entity.KnowledgeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface KnowledgeDocumentRepository extends JpaRepository<KnowledgeDocument, UUID> {

    Optional<KnowledgeDocument> findByIdAndDeletedFalse(UUID id);
}