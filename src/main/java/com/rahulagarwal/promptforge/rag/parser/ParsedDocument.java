package com.rahulagarwal.promptforge.rag.parser;

import lombok.Builder;

import java.util.Map;

@Builder
public record ParsedDocument(

        String content,

        Integer pageCount,

        Integer wordCount,

        Map<String, Object> metadata

) {
}