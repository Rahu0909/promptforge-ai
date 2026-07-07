package com.rahulagarwal.promptforge.prompt.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptResponse;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptVersionResponse;
import com.rahulagarwal.promptforge.prompt.entity.Prompt;
import com.rahulagarwal.promptforge.prompt.entity.PromptVersion;
import com.rahulagarwal.promptforge.prompt.mapper.PromptMapper;
import com.rahulagarwal.promptforge.prompt.repository.PromptRepository;
import com.rahulagarwal.promptforge.prompt.repository.PromptVersionRepository;
import com.rahulagarwal.promptforge.prompt.service.PromptVersionService;
import com.rahulagarwal.promptforge.security.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PromptVersionServiceImpl implements PromptVersionService {

    private final PromptVersionRepository repository;
    private final PromptRepository promptRepository;
    private final PromptMapper promptMapper;
    private final AuthorizationService authorizationService;

    @Override
    public void createInitialVersion(Prompt prompt) {
        PromptVersion version = new PromptVersion();
        version.setPrompt(prompt);
        version.setVersionNumber(1);
        version.setTitle(prompt.getTitle());
        version.setDescription(prompt.getDescription());
        version.setContent(prompt.getContent());
        version.setChangeSummary("Initial version");
        repository.save(version);
    }

    @Override
    public void createNewVersion(Prompt prompt, String changeSummary) {
        PromptVersion version = new PromptVersion();
        version.setPrompt(prompt);
        version.setVersionNumber(prompt.getLatestVersion());
        version.setTitle(prompt.getTitle());
        version.setDescription(prompt.getDescription());
        version.setContent(prompt.getContent());
        version.setChangeSummary(changeSummary);
        repository.save(version);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromptVersionResponse> getVersions(UUID promptId) {
        authorizationService.getOwnedPrompt(promptId);
        return repository.findByPromptIdOrderByVersionNumberDesc(promptId).stream().map(version -> new PromptVersionResponse(version.getVersionNumber(), version.getTitle(), version.getDescription(), version.getContent(), version.getChangeSummary())).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PromptVersionResponse getVersion(UUID promptId, Integer version) {
        authorizationService.getOwnedPrompt(promptId);
        PromptVersion promptVersion = repository.findByPromptIdAndVersionNumber(promptId, version).orElseThrow(() -> new ResourceNotFoundException("Prompt version not found.", ErrorCode.RESOURCE_NOT_FOUND));
        return new PromptVersionResponse(promptVersion.getVersionNumber(), promptVersion.getTitle(), promptVersion.getDescription(), promptVersion.getContent(), promptVersion.getChangeSummary());
    }

    @Override
    public PromptResponse restoreVersion(UUID promptId, Integer version) {
        Prompt prompt = authorizationService.getOwnedPrompt(promptId);
        PromptVersion promptVersion = repository.findByPromptIdAndVersionNumber(promptId, version).orElseThrow(() -> new ResourceNotFoundException("Prompt version not found.", ErrorCode.RESOURCE_NOT_FOUND));
        prompt.setTitle(promptVersion.getTitle());
        prompt.setDescription(promptVersion.getDescription());
        prompt.setContent(promptVersion.getContent());
        prompt.setLatestVersion(prompt.getLatestVersion() + 1);
        Prompt updated = promptRepository.save(prompt);
        createNewVersion(updated, "Restored from version " + version);
        return promptMapper.toResponse(updated);
    }
}