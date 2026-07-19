package com.rahulagarwal.promptforge.rag.storage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "rag.storage")
public class StorageProperties {

    /**
     * local | minio
     */
    private String provider;

    /**
     * Used only for Local Storage
     */
    private String location;

    /**
     * Used only for MinIO
     */
    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucket;

}