package com.rahulagarwal.promptforge.auth.entity;

import com.rahulagarwal.promptforge.common.entity.BaseEntity;
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
        name = "auth_password_reset_tokens",
        indexes = {
                @Index(name = "idx_password_reset_token", columnList = "token"),
                @Index(name = "idx_password_reset_user", columnList = "auth_user_id")
        }
)
public class PasswordResetToken extends BaseEntity {

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "auth_user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_password_reset_user")
    )
    private AuthUser authUser;

    @Column(nullable = false)
    private OffsetDateTime expiresAt;

    @Column(nullable = false)
    private boolean used = false;

}