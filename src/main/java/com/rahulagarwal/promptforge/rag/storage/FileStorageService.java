package com.rahulagarwal.promptforge.rag.storage;

import com.rahulagarwal.promptforge.rag.storage.model.StoredFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorageService {

    StoredFile store(MultipartFile file);

    InputStream load(String storagePath);

    void delete(String storagePath);

}