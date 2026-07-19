package com.rahulagarwal.promptforge.rag.storage.minio;

import com.rahulagarwal.promptforge.rag.exception.FileStorageException;
import com.rahulagarwal.promptforge.rag.storage.FileStorageService;
import com.rahulagarwal.promptforge.rag.storage.config.StorageProperties;
import com.rahulagarwal.promptforge.rag.storage.model.StoredFile;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "rag.storage", name = "provider", havingValue = "minio")
public class MinioFileStorageService implements FileStorageService {
    private final MinioClient minioClient;
    private final StorageProperties storageProperties;

    @Override
    public StoredFile store(MultipartFile file) {
        try {
            String extension = StringUtils.getFilenameExtension(Objects.requireNonNull(file.getOriginalFilename()));
            String storedFilename = UUID.randomUUID() + (extension == null ? "" : "." + extension);
            minioClient.putObject(PutObjectArgs.builder().bucket(storageProperties.getBucket()).object(storedFilename).stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());
            log.info("Stored '{}' in bucket '{}'", storedFilename, storageProperties.getBucket());
            return StoredFile.builder().storagePath(storedFilename).storedFilename(storedFilename).originalFilename(file.getOriginalFilename()).contentType(file.getContentType()).size(file.getSize()).build();
        } catch (Exception ex) {
            log.error("Failed to upload file to MinIO.", ex);
            throw new FileStorageException("Failed to upload file to MinIO.");
        }
    }

    @Override
    public void delete(String storagePath) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(storageProperties.getBucket()).object(storagePath).build());
            log.info("Deleted '{}' from MinIO", storagePath);
        } catch (Exception ex) {
            log.error("Unable to delete '{}'", storagePath, ex);
            throw new FileStorageException("Failed to delete object from MinIO.");
        }
    }

    @Override
    public InputStream load(String storagePath) {
        try {
            log.info("Loading '{}' from MinIO", storagePath);
            return minioClient.getObject(GetObjectArgs.builder().bucket(storageProperties.getBucket()).object(storagePath).build());
        } catch (Exception ex) {
            log.error("Unable to load '{}' from MinIO", storagePath, ex);
            throw new FileStorageException("Failed to load object from MinIO.");
        }
    }
}