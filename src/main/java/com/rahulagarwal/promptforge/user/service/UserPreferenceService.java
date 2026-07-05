package com.rahulagarwal.promptforge.user.service;

import com.rahulagarwal.promptforge.user.dto.request.UpdateUserPreferenceRequest;
import com.rahulagarwal.promptforge.user.dto.response.UserPreferenceResponse;

public interface UserPreferenceService {

    UserPreferenceResponse getMyPreferences();

    UserPreferenceResponse updatePreferences(UpdateUserPreferenceRequest request);

}