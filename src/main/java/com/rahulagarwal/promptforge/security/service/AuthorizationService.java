package com.rahulagarwal.promptforge.security.service;

import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.prompt.entity.Prompt;

import java.util.UUID;

public interface AuthorizationService {

    Project getOwnedProject(UUID projectId);

    Prompt getOwnedPrompt(UUID promptId);

}