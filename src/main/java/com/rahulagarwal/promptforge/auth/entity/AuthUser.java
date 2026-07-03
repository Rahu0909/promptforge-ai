package com.rahulagarwal.promptforge.auth.entity;

import com.rahulagarwal.promptforge.auth.enums.AccountStatus;
import com.rahulagarwal.promptforge.auth.enums.AuthProvider;
import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import com.rahulagarwal.promptforge.user.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "auth_users",
        indexes = {
                @Index(name = "idx_auth_user_email", columnList = "email"),
                @Index(name = "idx_auth_user_status", columnList = "account_status")
        }
)
public class AuthUser extends BaseEntity {

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = 30)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AuthProvider authProvider;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(nullable = false)
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    private boolean accountNonExpired = true;

    @Column(nullable = false)
    private boolean credentialsNonExpired = true;

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }


    public void verifyEmail() {
        this.emailVerified = true;
        this.accountStatus = AccountStatus.ACTIVE;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }
}