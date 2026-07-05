package com.rahulagarwal.promptforge.user.entity;

import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import com.rahulagarwal.promptforge.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "user_profiles",
        indexes = {
                @Index(name = "idx_user_profile_auth_user", columnList = "auth_user_id"),
                @Index(name = "idx_user_profile_display_name", columnList = "display_name")
        }
)
public class UserProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "auth_user_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_user_profile_auth_user")
    )
    private AuthUser authUser;

    @Column(nullable = false, length = 60)
    private String firstName;

    @Column(nullable = false, length = 60)
    private String lastName;

    @Column(nullable = false, unique = true, length = 120)
    private String displayName;

    @Column(length = 500)
    private String bio;

    @Column(length = 500)
    private String avatarUrl;

    @Column(nullable = false, length = 80)
    private String timezone = "UTC";

    @Column(nullable = false, length = 20)
    private String language = "en";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UserStatus status = UserStatus.ACTIVE;

    public void suspend() {
        this.status = UserStatus.SUSPENDED;
    }

    public void activate() {
        this.status = UserStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
    }

    public void delete() {
        this.status = UserStatus.DELETED;
        this.avatarUrl = null;
        this.bio = null;
    }

    public void updateStatus(UserStatus status) {
        switch (status) {
            case ACTIVE -> activate();
            case INACTIVE -> deactivate();
            case SUSPENDED -> suspend();
            case DELETED -> delete();
        }
    }
}