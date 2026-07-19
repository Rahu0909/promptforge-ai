package com.rahulagarwal.promptforge.rag.service;

import com.rahulagarwal.promptforge.rag.dto.response.RagChatResponse;

import java.util.UUID;

public interface RagChatService {

    RagChatResponse chat(UUID projectId, String question, Integer topK);

}