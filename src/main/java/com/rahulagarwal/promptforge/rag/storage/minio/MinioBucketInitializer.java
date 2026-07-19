package com.rahulagarwal.promptforge.rag.storage.minio;

import com.rahulagarwal.promptforge.rag.storage.config.StorageProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "rag.storage", name = "provider", havingValue = "minio")
public class MinioBucketInitializer {
    private final MinioClient minioClient;
    private final StorageProperties storageProperties;

    @PostConstruct
    public void initializeBucket() {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(storageProperties.getBucket()).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(storageProperties.getBucket()).build());
                log.info("Created MinIO bucket '{}'", storageProperties.getBucket());
            } else {
                log.info("MinIO bucket '{}' already exists", storageProperties.getBucket());
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to initialize MinIO bucket.", ex);
        }
    }
}