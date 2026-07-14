package com.rahulagarwal.promptforge.export.strategy;

import com.rahulagarwal.promptforge.codegen.entity.CodeGeneration;
import com.rahulagarwal.promptforge.export.enums.ExportType;
import org.springframework.core.io.ByteArrayResource;

public interface ExportStrategy {

    ExportType getType();

    ByteArrayResource export(CodeGeneration generation);

    String getFileName(CodeGeneration generation);

    String getContentType();

}