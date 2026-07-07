package com.rahulagarwal.promptforge.prompt.service;

import com.rahulagarwal.promptforge.prompt.dto.response.PromptResponse;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptVersionResponse;
import com.rahulagarwal.promptforge.prompt.entity.Prompt;

import java.util.List;
import java.util.UUID;

public interface PromptVersionService {

    void createInitialVersion(Prompt prompt);

    void createNewVersion(Prompt prompt, String changeSummary);

    List<PromptVersionResponse> getVersions(UUID promptId);

    PromptVersionResponse getVersion(UUID promptId, Integer version);

    PromptResponse restoreVersion(UUID promptId, Integer version);

}