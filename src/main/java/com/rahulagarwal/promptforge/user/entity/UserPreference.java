package com.rahulagarwal.promptforge.user.entity;

import com.rahulagarwal.promptforge.common.entity.BaseEntity;
import com.rahulagarwal.promptforge.user.enums.AiProviderPreference;
import com.rahulagarwal.promptforge.user.enums.Theme;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_preferences")
public class UserPreference extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_profile_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_user_preference_profile")
    )
    private UserProfile userProfile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Theme theme = Theme.SYSTEM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AiProviderPreference preferredAiProvider =
            AiProviderPreference.GEMINI;

    @Column(nullable = false)
    private boolean streamingEnabled = true;

    @Column(nullable = false)
    private boolean emailNotifications = true;

    @Column(nullable = false)
    private boolean promptAutoSave = true;

}