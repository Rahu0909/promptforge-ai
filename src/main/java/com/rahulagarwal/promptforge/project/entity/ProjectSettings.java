package com.rahulagarwal.promptforge.project.entity;

import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import com.rahulagarwal.promptforge.project.enums.AiProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "project_settings",
        indexes = {
                @Index(name = "idx_project_settings_project", columnList = "project_id")
        }
)
public class ProjectSettings extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "project_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_project_settings_project")
    )
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AiProvider provider = AiProvider.OPENAI;

    @Column(nullable = false, length = 100)
    private String model = "gpt-4.1-mini";

    @Column(nullable = false)
    private Double temperature = 0.7;

    @Column(nullable = false)
    private Integer maxTokens = 2048;

    @Column(nullable = false)
    private Boolean streamingEnabled = true;

    @Column(nullable = false)
    private Boolean ragEnabled = false;

    @Column(nullable = false)
    private Boolean memoryEnabled = true;

    @Column(length = 5000)
    private String systemPrompt;
}