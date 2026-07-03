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
        name = "auth_email_verification_tokens",
        indexes = {
                @Index(name = "idx_email_verification_token", columnList = "token"),
                @Index(name = "idx_email_verification_user", columnList = "auth_user_id")
        }
)
public class EmailVerificationToken extends BaseEntity {

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "auth_user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_email_verification_user")
    )
    private AuthUser authUser;

    @Column(nullable = false)
    private OffsetDateTime expiresAt;

    @Column(nullable = false)
    private boolean verified = false;

}