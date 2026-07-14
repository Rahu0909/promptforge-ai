package com.rahulagarwal.promptforge.export.service;

import com.rahulagarwal.promptforge.export.enums.ExportType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ExportService {

    ResponseEntity<Resource> exportCodeGeneration(UUID generationId, ExportType exportType);

}