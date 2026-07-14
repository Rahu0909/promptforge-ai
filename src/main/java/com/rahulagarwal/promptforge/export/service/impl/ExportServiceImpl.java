package com.rahulagarwal.promptforge.export.service.impl;

import com.rahulagarwal.promptforge.codegen.entity.CodeGeneration;
import com.rahulagarwal.promptforge.codegen.repository.CodeGenerationRepository;
import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.export.enums.ExportType;
import com.rahulagarwal.promptforge.export.factory.ExportStrategyFactory;
import com.rahulagarwal.promptforge.export.service.ExportService;
import com.rahulagarwal.promptforge.export.strategy.ExportStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExportServiceImpl implements ExportService {

    private final CodeGenerationRepository codeGenerationRepository;
    private final ExportStrategyFactory exportStrategyFactory;

    @Override
    public ResponseEntity<Resource> exportCodeGeneration(UUID generationId, ExportType exportType) {
        CodeGeneration generation = codeGenerationRepository.findById(generationId).orElseThrow(() ->
                new ResourceNotFoundException("Code generation not found.", ErrorCode.RESOURCE_NOT_FOUND));
        ExportStrategy strategy = exportStrategyFactory.getStrategy(exportType);
        ByteArrayResource resource = strategy.export(generation);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(strategy.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(strategy.getFileName(generation)).build().toString()).body(resource);
    }
}