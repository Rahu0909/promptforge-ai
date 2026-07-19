ALTER TABLE knowledge_documents
    ADD COLUMN deleted BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE knowledge_documents
    ADD COLUMN deleted_at TIMESTAMP WITH TIME ZONE;