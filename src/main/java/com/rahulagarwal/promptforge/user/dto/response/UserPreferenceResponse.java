package com.rahulagarwal.promptforge.user.dto.response;

import com.rahulagarwal.promptforge.user.enums.AiProviderPreference;
import com.rahulagarwal.promptforge.user.enums.Theme;

public record UserPreferenceResponse(
        Theme theme,
        AiProviderPreference preferredAiProvider,
        boolean streamingEnabled,
        boolean emailNotifications,
        boolean promptAutoSave
) {
}