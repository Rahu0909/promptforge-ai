package com.rahulagarwal.promptforge.rag.retrieval.service;

import com.rahulagarwal.promptforge.rag.retrieval.RetrievedChunk;

import java.util.List;
import java.util.UUID;

public interface DocumentRetrievalService {

    List<RetrievedChunk> retrieve(UUID projectId, String question, int topK);

}