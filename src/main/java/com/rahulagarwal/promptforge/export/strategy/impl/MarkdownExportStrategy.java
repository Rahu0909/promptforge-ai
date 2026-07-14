package com.rahulagarwal.promptforge.export.strategy.impl;

import com.rahulagarwal.promptforge.codegen.entity.CodeGeneration;
import com.rahulagarwal.promptforge.export.enums.ExportType;
import com.rahulagarwal.promptforge.export.strategy.ExportStrategy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MarkdownExportStrategy implements ExportStrategy {

    @Override
    public ExportType getType() {
        return ExportType.MARKDOWN;
    }

    @Override
    public ByteArrayResource export(CodeGeneration generation) {

        String markdown = """
                # PromptForge AI Code Generation
                ## Prompt
                %s
                ## Language
                %s
                ## Framework
                %s
                ## Generated Code
                ```%s
                %s
                ```
                """.formatted(generation.getPrompt(), generation.getLanguage(), generation.getFramework(), generation.getLanguage().toLowerCase(), generation.getGeneratedCode());
        return new ByteArrayResource(markdown.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String getFileName(CodeGeneration generation) {
        return "generation-" + generation.getId() + ".md";
    }

    @Override
    public String getContentType() {
        return "text/markdown";
    }
}