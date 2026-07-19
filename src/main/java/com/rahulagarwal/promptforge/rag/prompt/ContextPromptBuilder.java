package com.rahulagarwal.promptforge.rag.prompt;

import com.rahulagarwal.promptforge.rag.retrieval.RetrievedChunk;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContextPromptBuilder {
    public String buildPrompt(String question, List<RetrievedChunk> chunks) {
        StringBuilder builder = new StringBuilder();
        builder.append("""
                You are PromptForge AI.
               
                Answer ONLY from the supplied context.
                
                If the answer cannot be found in the context, clearly say:
                
                "I could not find this information in the uploaded documents."
                
                Never invent information.
                
                ------------------------
                CONTEXT
                ------------------------
                
                """);

        for (RetrievedChunk chunk : chunks) {
            builder.append("Document: ").append(chunk.documentName()).append("\n");
            builder.append("Chunk: ").append(chunk.chunkIndex()).append("\n");
            builder.append(chunk.content()).append("\n\n");
        }
        builder.append("""
                ------------------------
                QUESTION
                ------------------------
                """);
        builder.append(question);
        builder.append("""
                
                ------------------------
                ANSWER
                ------------------------
                """);

        return builder.toString();
    }

}