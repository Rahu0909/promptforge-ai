package com.rahulagarwal.promptforge.ai.util;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PromptTemplateService {

    public String buildPrompt(String template, Map<String, Object> variables) {
        PromptTemplate promptTemplate = new PromptTemplate(template);
        return promptTemplate.render(variables);
    }

}