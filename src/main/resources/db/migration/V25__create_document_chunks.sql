CREATE TABLE document_chunks
(
    id UUID PRIMARY KEY,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    document_id UUID NOT NULL,

    chunk_index INTEGER NOT NULL,

    content TEXT NOT NULL,

    token_count INTEGER NOT NULL,

    embedding VECTOR(1536),

    CONSTRAINT fk_document_chunks_document
        FOREIGN KEY (document_id)
            REFERENCES knowledge_documents(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_document_chunks_document
    ON document_chunks(document_id);

CREATE INDEX idx_document_chunks_embedding
    ON document_chunks
    USING hnsw (embedding vector_cosine_ops);