package com.rahulagarwal.promptforge.user.mapper;

import com.rahulagarwal.promptforge.user.dto.request.UpdateUserPreferenceRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserPreferenceResponse;
import com.rahulagarwal.promptforge.user.entity.UserPreference;
import org.springframework.stereotype.Component;

@Component
public class UserPreferenceMapper {

    public UserPreferenceResponse toResponse(UserPreference preference) {
        return new UserPreferenceResponse(
                preference.getTheme(),
                preference.getPreferredAiProvider(),
                preference.isStreamingEnabled(),
                preference.isEmailNotifications(),
                preference.isPromptAutoSave()
        );
    }

    public void update(UserPreference preference,
                       UpdateUserPreferenceRequest request) {

        if (request.theme() != null) {
            preference.setTheme(request.theme());
        }

        if (request.preferredAiProvider() != null) {
            preference.setPreferredAiProvider(request.preferredAiProvider());
        }

        if (request.streamingEnabled() != null) {
            preference.setStreamingEnabled(request.streamingEnabled());
        }

        if (request.emailNotifications() != null) {
            preference.setEmailNotifications(request.emailNotifications());
        }

        if (request.promptAutoSave() != null) {
            preference.setPromptAutoSave(request.promptAutoSave());
        }
    }
}