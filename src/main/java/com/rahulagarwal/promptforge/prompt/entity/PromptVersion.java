package com.rahulagarwal.promptforge.prompt.entity;

import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "prompt_versions", uniqueConstraints = {@UniqueConstraint(name = "uk_prompt_version", columnNames = {"prompt_id", "version_number"})}, indexes = {@Index(name = "idx_prompt_version_prompt", columnList = "prompt_id")})
public class PromptVersion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prompt_id", nullable = false, foreignKey = @ForeignKey(name = "fk_prompt_version_prompt"))
    private Prompt prompt;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 500)
    private String changeSummary;
}