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

    public static final String CODE_GENERATION = """
            You are an expert {language} software architect.
            
            Generate production-grade code.
            
            Requirements:
            
            - Framework: {framework}
            - Follow SOLID principles.
            - Follow Clean Architecture.
            - Follow Clean Code.
            - No TODO comments.
            - No placeholder implementations.
            - No explanations.
            - No markdown.
            - Return ONLY compilable source code.
            
            Task:
            
            {task}
            """;
    public static final String STRUCTURED_CODE_GENERATION = """
            You are a Senior {language} Software Architect.
            
            Generate production-grade source code.
            
            Task:
            
            {task}
            
            Framework:
            {framework}
            
            Return ONLY a valid JSON object matching the required schema.
            
            Do not return markdown.
            
            Do not wrap in triple backticks.
            
            Do not explain anything.
            """;
}