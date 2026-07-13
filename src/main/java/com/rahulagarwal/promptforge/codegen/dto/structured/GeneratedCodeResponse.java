package com.rahulagarwal.promptforge.codegen.dto.structured;

import java.util.List;

public record GeneratedCodeResponse(

        String title,

        String language,

        String framework,

        String description,

        List<String> imports,

        String code

) {
}