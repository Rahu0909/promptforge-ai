package com.rahulagarwal.promptforge.chat.entity;

import com.rahulagarwal.promptforge.chat.enums.MessageRole;
import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "messages",
        indexes = {
                @Index(name = "idx_message_conversation", columnList = "conversation_id"),
                @Index(name = "idx_message_role", columnList = "role")
        }
)
public class Message extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "conversation_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_message_conversation")
    )
    private Conversation conversation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MessageRole role;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "token_count")
    private Integer tokenCount;

    @Column(name = "generation_time_ms")
    private Long generationTimeMs;

}