package com.rahulagarwal.promptforge.rag.parser;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentParser {

    boolean supports(String filename);

    String parse(MultipartFile file);

}