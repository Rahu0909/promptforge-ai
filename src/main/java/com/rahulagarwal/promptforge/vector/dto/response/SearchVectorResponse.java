package com.rahulagarwal.promptforge.vector.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchVectorResponse(

        Integer totalMatches,

        List<SearchResult> results

) {
}