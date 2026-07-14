package com.rahulagarwal.promptforge.export.strategy.impl;

import com.rahulagarwal.promptforge.codegen.entity.CodeGeneration;
import com.rahulagarwal.promptforge.export.enums.ExportType;
import com.rahulagarwal.promptforge.export.strategy.ExportStrategy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipExportStrategy implements ExportStrategy {

    @Override
    public ExportType getType() {
        return ExportType.ZIP;
    }

    @Override
    public ByteArrayResource export(CodeGeneration generation) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            ZipEntry entry = new ZipEntry(resolveFileName(generation));
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write(generation.getGeneratedCode().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            return new ByteArrayResource(outputStream.toByteArray());
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create ZIP export.", ex);
        }
    }

    @Override
    public String getFileName(CodeGeneration generation) {
        return "generation-" + generation.getId() + ".zip";
    }

    @Override
    public String getContentType() {
        return "application/zip";
    }

    private String resolveFileName(CodeGeneration generation) {
        String language = generation.getLanguage();
        if (language == null) {
            return "generated.txt";
        }
        return switch (language.toLowerCase()) {
            case "java" -> "Generated.java";
            case "python" -> "generated.py";
            case "javascript" -> "generated.js";
            case "typescript" -> "generated.ts";
            default -> "generated.txt";
        };
    }
}