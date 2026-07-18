package com.rahulagarwal.promptforge.vector.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.rahulagarwal.promptforge.vector.enums.SourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SaveVectorRequest(

        @NotNull
        UUID projectId,

        @NotNull
        SourceType sourceType,

        @NotNull
        UUID sourceId,

        @NotBlank
        String content,

        JsonNode metadata

) {
}