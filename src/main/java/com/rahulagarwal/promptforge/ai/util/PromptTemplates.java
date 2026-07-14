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
            You are an expert {language} software architect.
            
            Generate production-ready source code.
            
            Framework:
            {framework}
            
            Task:
            {task}
            
            STRICT OUTPUT RULES
            
            You are returning data to another computer program.
            
            Return ONLY one valid JSON object.
            
            The response must start with an opening curly brace.
            
            The response must end with a closing curly brace.
            
            Do not write explanations.
            
            Do not write markdown.
            
            Do not use triple backticks.
            
            Do not write any text before the JSON.
            
            Do not write any text after the JSON.
            
            Do not add extra fields.
            
            Do not omit required fields.
            
            The value of the code field must contain the complete source code.
            """;
}