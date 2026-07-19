package com.rahulagarwal.promptforge.rag.parser;

import com.rahulagarwal.promptforge.rag.enums.DocumentType;

import java.io.InputStream;

public interface DocumentParser {

    DocumentType supportedType();

    ParsedDocument parse(InputStream inputStream);
}