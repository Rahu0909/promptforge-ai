package com.rahulagarwal.promptforge.rag.chunk;

import com.rahulagarwal.promptforge.rag.parser.ParsedDocument;

import java.util.List;

public interface DocumentChunker {

    List<DocumentChunk> chunk(ParsedDocument document);

}