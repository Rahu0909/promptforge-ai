package com.rahulagarwal.promptforge.rag.entity;

import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.rag.enums.DocumentStatus;
import com.rahulagarwal.promptforge.rag.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "knowledge_documents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDocument extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "document_name", nullable = false)
    private String documentName;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status;

    @Column(name = "total_chunks", nullable = false)
    private Integer totalChunks;

    @Column(name = "processed_at")
    private OffsetDateTime processedAt;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
}