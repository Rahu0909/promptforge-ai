package com.rahulagarwal.promptforge.rag.parser.pdf;

import com.rahulagarwal.promptforge.rag.enums.DocumentType;
import com.rahulagarwal.promptforge.rag.exception.DocumentParsingException;
import com.rahulagarwal.promptforge.rag.parser.DocumentParser;
import com.rahulagarwal.promptforge.rag.parser.ParsedDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PdfDocumentParser implements DocumentParser {

    @Override
    public DocumentType supportedType() {
        return DocumentType.PDF;
    }

    @Override
    public ParsedDocument parse(InputStream inputStream) {
        try (PDDocument document = Loader.loadPDF(RandomAccessReadBuffer.createBufferFromStream(inputStream))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            Map<String, Object> metadata = new HashMap<>();
            String author = document.getDocumentInformation().getAuthor();
            String title = document.getDocumentInformation().getTitle();
            if (author != null) {
                metadata.put("author", author);
            }
            if (title != null) {
                metadata.put("title", title);
            }
            return ParsedDocument.builder().content(text)
                    .pageCount(document.getNumberOfPages())
                    .wordCount(calculateWords(text))
                    .metadata(metadata).build();
        } catch (IOException ex) {
            log.error("Unable to parse pdf.", ex);
            throw new DocumentParsingException("Unable to parse PDF document.");
        }
    }

    private Integer calculateWords(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        return text.trim().split("\\s+").length;

    }

}