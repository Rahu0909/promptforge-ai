package com.rahulagarwal.promptforge.codegen.entity;

import com.rahulagarwal.promptforge.codegen.enums.CodeGenerationStatus;
import com.rahulagarwal.promptforge.codegen.enums.GenerationType;
import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import com.rahulagarwal.promptforge.project.entity.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "code_generations",
        indexes = {
                @Index(name = "idx_codegen_project", columnList = "project_id"),
                @Index(name = "idx_codegen_type", columnList = "generation_type"),
                @Index(name = "idx_codegen_status", columnList = "status"),
                @Index(name = "idx_codegen_created", columnList = "created_at")
        }
)
public class CodeGeneration extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String prompt;

    @Enumerated(EnumType.STRING)
    @Column(name = "generation_type", nullable = false, length = 50)
    private GenerationType generationType;

    @Column(length = 50)
    private String language;

    @Column(length = 100)
    private String framework;

    @Column(name = "generated_code",
            nullable = false,
            columnDefinition = "TEXT")
    private String generatedCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CodeGenerationStatus status;

    @Column(nullable = false, length = 30)
    private String provider;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false)
    private Integer tokenCount;

    @Column(nullable = false)
    private Long generationTimeMs;

}