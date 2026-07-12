package com.rahulagarwal.promptforge.chat.entity;

import com.rahulagarwal.promptforge.chat.enums.ConversationStatus;
import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import com.rahulagarwal.promptforge.project.entity.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "conversations",
        indexes = {
                @Index(name = "idx_conversation_project", columnList = "project_id"),
                @Index(name = "idx_conversation_status", columnList = "status"),
                @Index(name = "idx_conversation_last_message", columnList = "last_message_at")
        }
)
public class Conversation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "project_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_conversation_project")
    )
    private Project project;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 50)
    private String provider;

    @Column(nullable = false, length = 100)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ConversationStatus status = ConversationStatus.ACTIVE;

    @Column(name = "last_message_at")
    private OffsetDateTime lastMessageAt;

}