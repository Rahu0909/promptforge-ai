package com.rahulagarwal.promptforge.rag.storage.local;

import com.rahulagarwal.promptforge.rag.exception.FileStorageException;
import com.rahulagarwal.promptforge.rag.storage.FileStorageService;
import com.rahulagarwal.promptforge.rag.storage.config.StorageProperties;
import com.rahulagarwal.promptforge.rag.storage.model.StoredFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "rag.storage", name = "provider", havingValue = "local", matchIfMissing = true)
public class LocalFileStorageService implements FileStorageService {

    private final StorageProperties storageProperties;

    @Override
    public StoredFile store(MultipartFile file) {
        try {
            Path uploadDirectory = Path.of(storageProperties.getLocation());
            Files.createDirectories(uploadDirectory);
            String extension = StringUtils.getFilenameExtension(Objects.requireNonNull(file.getOriginalFilename()));
            String storedFilename = UUID.randomUUID() + (extension == null ? "" : "." + extension);
            Path destination = uploadDirectory.resolve(storedFilename);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            log.info("Stored file '{}' at '{}'", file.getOriginalFilename(), destination);
            return StoredFile.builder().storagePath(destination.toString()).originalFilename(file.getOriginalFilename()).storedFilename(storedFilename).contentType(file.getContentType()).size(file.getSize()).build();
        } catch (IOException ex) {
            log.error("Failed to store file '{}'", file.getOriginalFilename(), ex);
            throw new FileStorageException("Failed to store uploaded file.");
        }

    }

    @Override
    public void delete(String storagePath) {
        try {
            Files.deleteIfExists(Path.of(storagePath));
            log.info("Deleted file '{}'", storagePath);
        } catch (IOException ex) {
            log.error("Unable to delete '{}'", storagePath, ex);
            throw new FileStorageException("Failed to delete stored file.");
        }
    }

    @Override
    public InputStream load(String storagePath) {
        try {
            Path filePath = Path.of(storagePath);
            log.info("Loading file '{}'", filePath);
            return Files.newInputStream(filePath);
        } catch (IOException ex) {
            log.error("Unable to load '{}'", storagePath, ex);
            throw new FileStorageException("Failed to load stored file.");
        }
    }
}