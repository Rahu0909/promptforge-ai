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
        name = "auth_refresh_tokens",
        indexes = {
                @Index(name = "idx_refresh_token_user", columnList = "auth_user_id"),
                @Index(name = "idx_refresh_token_revoked", columnList = "revoked"),
                @Index(name = "idx_refresh_token_expiry", columnList = "expires_at")
        }
)
public class RefreshToken extends BaseEntity {

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "auth_user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_refresh_token_user")
    )
    private AuthUser authUser;

    @Column(nullable = false)
    private OffsetDateTime expiresAt;

    @Column(nullable = false)
    private boolean revoked = false;

    @Column(length = 100)
    private String deviceName;

    @Column(length = 100)
    private String ipAddress;

}