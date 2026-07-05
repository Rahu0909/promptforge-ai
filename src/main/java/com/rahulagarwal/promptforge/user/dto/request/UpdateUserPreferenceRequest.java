package com.rahulagarwal.promptforge.user.dto.request;

import com.rahulagarwal.promptforge.user.enums.AiProviderPreference;
import com.rahulagarwal.promptforge.user.enums.Theme;

public record UpdateUserPreferenceRequest(
        Theme theme,
        AiProviderPreference preferredAiProvider,
        Boolean streamingEnabled,
        Boolean emailNotifications,
        Boolean promptAutoSave
) {
}