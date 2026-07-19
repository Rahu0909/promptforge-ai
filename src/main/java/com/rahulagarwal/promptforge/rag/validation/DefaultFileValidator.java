package com.rahulagarwal.promptforge.rag.validation;

import com.rahulagarwal.promptforge.rag.exception.EmptyFileException;
import com.rahulagarwal.promptforge.rag.exception.UnsupportedFileTypeException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Component
public class DefaultFileValidator implements FileValidator {
    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of("pdf", "txt", "md", "docx");
    @Override
    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new EmptyFileException();
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            throw new UnsupportedFileTypeException("unknown");
        }
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        if (!SUPPORTED_EXTENSIONS.contains(extension)) {
            throw new UnsupportedFileTypeException(extension);
        }
    }
}