package com.rahulagarwal.promptforge.rag.processing;

import com.rahulagarwal.promptforge.rag.chunk.DocumentChunk;
import com.rahulagarwal.promptforge.rag.chunk.DocumentChunker;
import com.rahulagarwal.promptforge.rag.embedding.EmbeddingGenerationService;
import com.rahulagarwal.promptforge.rag.entity.KnowledgeDocument;
import com.rahulagarwal.promptforge.rag.enums.DocumentStatus;
import com.rahulagarwal.promptforge.rag.exception.DocumentNotFoundException;
import com.rahulagarwal.promptforge.rag.parser.DocumentParser;
import com.rahulagarwal.promptforge.rag.parser.DocumentParserFactory;
import com.rahulagarwal.promptforge.rag.parser.ParsedDocument;
import com.rahulagarwal.promptforge.rag.repository.DocumentChunkRepository;
import com.rahulagarwal.promptforge.rag.repository.KnowledgeDocumentRepository;
import com.rahulagarwal.promptforge.rag.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentProcessingServiceImpl implements DocumentProcessingService {
    private final KnowledgeDocumentRepository documentRepository;
    private final FileStorageService storageService;
    private final DocumentParserFactory parserFactory;
    private final DocumentChunker documentChunker;
    private final DocumentChunkRepository chunkRepository;
    private final EmbeddingGenerationService embeddingGenerationService;
    @Override
    public void process(UUID documentId) {
        KnowledgeDocument document = documentRepository.findByIdAndDeletedFalse(documentId).orElseThrow(() -> new DocumentNotFoundException(documentId));
        log.info("Starting processing for document {}", document.getDocumentName());
        try (InputStream inputStream = storageService.load(document.getStoragePath())) {
            // Parse document
            DocumentParser parser = parserFactory.getParser(document.getDocumentType());
            ParsedDocument parsedDocument = parser.parse(inputStream);
            log.info("Document parsed successfully. Pages={}, Words={}", parsedDocument.pageCount(), parsedDocument.wordCount());
            // Chunk document
            List<DocumentChunk> chunks = documentChunker.chunk(parsedDocument);
            log.info("Generated {} chunks.", chunks.size());
            // Prepare entities
            List<com.rahulagarwal.promptforge.rag.entity.DocumentChunk> entities = new ArrayList<>(chunks.size());
            for (DocumentChunk chunk : chunks) {
                float[] embedding = embeddingGenerationService.generateEmbedding(chunk.content());
                entities.add(com.rahulagarwal.promptforge.rag.entity.DocumentChunk.builder().document(document).chunkIndex(chunk.chunkIndex()).content(chunk.content()).tokenCount(chunk.tokenCount()).embedding(embedding).build());
            }
            // Batch insert
            chunkRepository.saveAll(entities);
            // Update document metadata
            document.setChunkCount(chunks.size());
            document.setTotalChunks(chunks.size());
            document.setProcessedAt(OffsetDateTime.now());
            document.setStatus(DocumentStatus.READY);
            documentRepository.save(document);
            log.info("Completed processing document {} with {} chunks.", document.getDocumentName(), chunks.size());
        } catch (Exception ex) {
            log.error("Failed to process document {}", documentId, ex);
            document.setStatus(DocumentStatus.FAILED);
            documentRepository.save(document);
            throw new RuntimeException("Document processing failed.", ex);
        }
    }
}