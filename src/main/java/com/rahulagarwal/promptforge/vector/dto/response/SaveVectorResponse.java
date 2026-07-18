package com.rahulagarwal.promptforge.vector.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SaveVectorResponse(

        UUID embeddingId,

        Integer dimensions,

        String message

) {
}