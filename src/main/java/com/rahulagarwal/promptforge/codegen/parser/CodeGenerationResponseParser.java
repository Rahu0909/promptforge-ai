package com.rahulagarwal.promptforge.codegen.parser;

import org.springframework.stereotype.Component;

@Component
public class CodeGenerationResponseParser {

    public String parse(String aiResponse) {

        if (aiResponse == null) {
            return "";
        }

        String cleaned = aiResponse.trim();

        cleaned = cleaned.replaceAll("```java", "");
        cleaned = cleaned.replaceAll("```kotlin", "");
        cleaned = cleaned.replaceAll("```sql", "");
        cleaned = cleaned.replaceAll("```xml", "");
        cleaned = cleaned.replaceAll("```yaml", "");
        cleaned = cleaned.replaceAll("```", "");

        return cleaned.trim();
    }

}