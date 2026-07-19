package com.rahulagarwal.promptforge.rag.parser;

import com.rahulagarwal.promptforge.rag.enums.DocumentType;
import com.rahulagarwal.promptforge.rag.exception.FileValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultDocumentParserFactory implements DocumentParserFactory {

    private final List<DocumentParser> parsers;

    @Override
    public DocumentParser getParser(DocumentType documentType) {

        return parsers.stream()
                .filter(parser -> parser.supportedType() == documentType)
                .findFirst()
                .orElseThrow(() ->
                        new FileValidationException(
                                "No parser registered for " + documentType
                        ));
    }
}