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
@Table(name = "prompt_variables", uniqueConstraints = {@UniqueConstraint(name = "uk_prompt_variable", columnNames = {"prompt_id", "variable_name"})}, indexes = {@Index(name = "idx_prompt_variable_prompt", columnList = "prompt_id")})
public class PromptVariable extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prompt_id", nullable = false, foreignKey = @ForeignKey(name = "fk_prompt_variable_prompt"))
    private Prompt prompt;

    @Column(name = "variable_name", nullable = false, length = 100)
    private String variableName;

    @Column(length = 500)
    private String description;

    @Column(name = "default_value", length = 1000)
    private String defaultValue;

    @Column(nullable = false)
    private Boolean required = true;
}