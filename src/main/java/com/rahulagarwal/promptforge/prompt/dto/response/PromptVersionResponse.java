package com.rahulagarwal.promptforge.prompt.dto.response;

public record PromptVersionResponse(

        Integer version,

        String title,

        String description,

        String content,

        String changeSummary

) {
}