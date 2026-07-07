package com.rahulagarwal.promptforge.prompt.dto.request;

import jakarta.validation.constraints.NotNull;

public record ToggleFavoriteRequest(

        @NotNull
        Boolean favorite

) {
}