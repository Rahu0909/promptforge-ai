ALTER TABLE document_embeddings
    ADD COLUMN knowledge_document_id UUID;

ALTER TABLE document_embeddings
    ADD CONSTRAINT fk_document_embedding_knowledge_document
        FOREIGN KEY (knowledge_document_id)
            REFERENCES knowledge_documents(id);