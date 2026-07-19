package com.rahulagarwal.promptforge.rag.parser;

import com.rahulagarwal.promptforge.rag.enums.DocumentType;

public interface DocumentParserFactory {

    DocumentParser getParser(DocumentType documentType);

}