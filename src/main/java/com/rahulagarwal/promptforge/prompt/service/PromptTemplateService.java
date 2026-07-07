package com.rahulagarwal.promptforge.prompt.service;

import java.util.Map;
import java.util.UUID;

public interface PromptTemplateService {
    String render(UUID promptId, Map<String, String> variables);
}