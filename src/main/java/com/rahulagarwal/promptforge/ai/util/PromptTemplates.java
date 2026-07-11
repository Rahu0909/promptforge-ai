package com.rahulagarwal.promptforge.ai.util;

public final class PromptTemplates {

    private PromptTemplates() {
    }

    public static final String EXPLAIN_TOPIC = """
                You are a Senior Java Backend Architect with 15+ years of experience.
            
            Your task is to explain the given topic.
            
            Topic:
            {topic}
            
            STRICT INSTRUCTIONS:
            
            - Return EXACTLY {lines} bullet points.
            - Each bullet must contain ONLY ONE sentence.
            - The LAST bullet MUST contain exactly one practical example.
            - Do NOT write a title.
            - Do NOT write an introduction.
            - Do NOT write a conclusion.
            - Do NOT use markdown headings.
            - Return ONLY bullet points.
            
            """;

    public static final String INTERVIEW_QUESTION = """
            You are a Senior Java Interviewer.
            
            Generate one interview question.
            
            Topic:
            {topic}
            
            Return a valid response matching the requested schema.
            
            Do not add any explanation.
            """;
}