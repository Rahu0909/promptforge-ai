package com.rahulagarwal.promptforge.export.controller;

import com.rahulagarwal.promptforge.export.enums.ExportType;
import com.rahulagarwal.promptforge.export.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exports")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/{generationId}")
    public ResponseEntity<Resource> export(@PathVariable UUID generationId, @RequestParam ExportType type) {
        return exportService.exportCodeGeneration(generationId, type);

    }

}