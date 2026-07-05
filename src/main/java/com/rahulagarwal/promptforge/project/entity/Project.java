package com.rahulagarwal.promptforge.project.entity;

import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.project.enums.ProjectVisibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "projects",
        indexes = {
                @Index(name = "idx_project_owner", columnList = "owner_id"),
                @Index(name = "idx_project_status", columnList = "status"),
                @Index(name = "idx_project_visibility", columnList = "visibility"),
                @Index(name = "idx_project_name", columnList = "name")
        }
)
public class Project extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProjectStatus status = ProjectStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectVisibility visibility = ProjectVisibility.PRIVATE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "fk_project_owner")
    )
    private UserProfile owner;

    public void archive() {
        this.status = ProjectStatus.ARCHIVED;
    }

    public void activate() {
        this.status = ProjectStatus.ACTIVE;
    }

    public void delete() {
        this.status = ProjectStatus.DELETED;
    }

}