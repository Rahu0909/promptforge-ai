package com.rahulagarwal.promptforge.prompt.entity;

import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.prompt.enums.PromptStatus;
import com.rahulagarwal.promptforge.prompt.enums.PromptVisibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "prompts",
        indexes = {
                @Index(name = "idx_prompt_project", columnList = "project_id"),
                @Index(name = "idx_prompt_category", columnList = "category_id"),
                @Index(name = "idx_prompt_status", columnList = "status"),
                @Index(name = "idx_prompt_title", columnList = "title")
        }
)
public class Prompt extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "project_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_prompt_project")
    )
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_prompt_category")
    )
    private PromptCategory category;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PromptStatus status = PromptStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PromptVisibility visibility = PromptVisibility.PRIVATE;

    @Column(nullable = false)
    private Boolean favorite = false;

    @Column(nullable = false)
    private Integer latestVersion = 1;
}