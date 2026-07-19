package com.rahulagarwal.promptforge.rag.chunk;

import com.rahulagarwal.promptforge.rag.parser.ParsedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecursiveDocumentChunker implements DocumentChunker {
    private final ChunkingProperties properties;

    @Override
    public List<DocumentChunk> chunk(ParsedDocument document) {
        String text = document.content();
        if (text == null || text.isBlank()) {
            return List.of();
        }
        List<DocumentChunk> chunks = new ArrayList<>();
        int chunkSize = properties.getChunkSize();
        int overlap = properties.getOverlap();
        int start = 0;
        int index = 0;
        while (start < text.length()) {
            int end = Math.min(start + chunkSize, text.length());
            // Try to end on paragraph
            if (end < text.length()) {
                int paragraphBreak = text.lastIndexOf("\n\n", end);
                if (paragraphBreak > start + (chunkSize / 2)) {
                    end = paragraphBreak;
                } else {
                    int lineBreak = text.lastIndexOf('\n', end);
                    if (lineBreak > start + (chunkSize / 2)) {
                        end = lineBreak;
                    } else {
                        int space = text.lastIndexOf(' ', end);
                        if (space > start + (chunkSize / 2)) {
                            end = space;
                        }
                    }
                }
            }
            String chunk = text.substring(start, end).trim();
            if (!chunk.isBlank()) {
                chunks.add(DocumentChunk.builder().chunkIndex(index++).content(chunk).tokenCount(estimateTokens(chunk)).build());
            }
            if (end >= text.length()) {
                break;
            }
            start = Math.max(end - overlap, start + 1);
        }
        return chunks;
    }

    /**
     * Rough estimation.
     * 1 token ≈ 4 characters.
     */
    private int estimateTokens(String text) {
        return Math.max(1, text.length() / 4);
    }
}