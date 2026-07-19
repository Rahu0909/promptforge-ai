package com.rahulagarwal.promptforge.rag.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.project.repository.ProjectRepository;
import com.rahulagarwal.promptforge.rag.chunk.DocumentChunker;
import com.rahulagarwal.promptforge.rag.dto.response.UploadDocumentResponse;
import com.rahulagarwal.promptforge.rag.embedding.EmbeddingGenerationService;
import com.rahulagarwal.promptforge.rag.entity.KnowledgeDocument;
import com.rahulagarwal.promptforge.rag.enums.DocumentStatus;
import com.rahulagarwal.promptforge.rag.enums.DocumentType;
import com.rahulagarwal.promptforge.rag.processing.DocumentProcessingService;
import com.rahulagarwal.promptforge.rag.repository.DocumentChunkRepository;
import com.rahulagarwal.promptforge.rag.repository.KnowledgeDocumentRepository;
import com.rahulagarwal.promptforge.rag.service.DocumentService;
import com.rahulagarwal.promptforge.rag.storage.FileStorageService;
import com.rahulagarwal.promptforge.rag.storage.model.StoredFile;
import com.rahulagarwal.promptforge.rag.validation.FileValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final KnowledgeDocumentRepository knowledgeDocumentRepository;
    private final ProjectRepository projectRepository;
    private final FileStorageService fileStorageService;
    private final FileValidator fileValidator;
    private final DocumentProcessingService documentProcessingService;
    @Override
    @Transactional
    public UploadDocumentResponse upload(UUID projectId, MultipartFile file) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found."));
        fileValidator.validate(file);
        DocumentType documentType = resolveType(file);
        StoredFile storedFile = fileStorageService.store(file);
        try {
            KnowledgeDocument document = KnowledgeDocument.builder().project(project).documentName(extractDocumentName(file.getOriginalFilename())).originalFileName(file.getOriginalFilename()).documentType(documentType).status(DocumentStatus.PROCESSING).storagePath(storedFile.storagePath()).totalChunks(0).chunkCount(0).build();
            knowledgeDocumentRepository.save(document);
            documentProcessingService.process(document.getId());
            return UploadDocumentResponse.builder().documentId(document.getId()).documentName(document.getDocumentName())
                    .status(DocumentStatus.READY.name()).build();
        } catch (Exception ex) {
            fileStorageService.delete(storedFile.storagePath());
            throw ex;
        }
    }

    private DocumentType resolveType(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new RuntimeException("Invalid file.");
        }
        filename = filename.toLowerCase();
        if (filename.endsWith(".pdf")) {
            return DocumentType.PDF;
        }
        if (filename.endsWith(".txt")) {
            return DocumentType.TXT;
        }
        if (filename.endsWith(".md")) {
            return DocumentType.MARKDOWN;
        }
        if (filename.endsWith(".docx")) {
            return DocumentType.DOCX;
        }
        throw new RuntimeException("Unsupported document type.");
    }

    private String extractDocumentName(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return filename;
        }
        return filename.substring(0, index);
    }

    @Override
    @Transactional
    public void delete(UUID documentId) {
        KnowledgeDocument document = knowledgeDocumentRepository.findByIdAndDeletedFalse(documentId).orElseThrow(() -> new ResourceNotFoundException("Document not found.", ErrorCode.RESOURCE_NOT_FOUND));
        fileStorageService.delete(document.getStoragePath());
        document.setDeleted(true);
        document.setDeletedAt(OffsetDateTime.now());
        knowledgeDocumentRepository.save(document);
    }
}