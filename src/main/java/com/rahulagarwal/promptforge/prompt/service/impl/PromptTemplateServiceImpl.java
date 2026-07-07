package com.rahulagarwal.promptforge.prompt.service.impl;

import com.rahulagarwal.promptforge.prompt.entity.Prompt;
import com.rahulagarwal.promptforge.prompt.service.PromptTemplateService;
import com.rahulagarwal.promptforge.security.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromptTemplateServiceImpl implements PromptTemplateService {

    private final AuthorizationService authorizationService;

    @Override
    public String render(UUID promptId, Map<String, String> variables) {
        Prompt prompt = authorizationService.getOwnedPrompt(promptId);
        String result = prompt.getContent();
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }
}