package com.rahulagarwal.promptforge.rag.storage.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "rag.storage",
        name = "provider",
        havingValue = "minio"
)
public class MinioConfiguration {

    private final StorageProperties storageProperties;

    @Bean
    public MinioClient minioClient() {

        return MinioClient.builder()
                .endpoint(storageProperties.getEndpoint())
                .credentials(
                        storageProperties.getAccessKey(),
                        storageProperties.getSecretKey()
                )
                .build();

    }

}