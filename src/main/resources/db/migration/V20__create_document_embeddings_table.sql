CREATE TABLE document_embeddings
(
    id UUID PRIMARY KEY,

    project_id UUID NOT NULL,

    source_type VARCHAR(50) NOT NULL,

    source_id UUID NOT NULL,

    content TEXT NOT NULL,

    embedding VECTOR(768) NOT NULL,

    metadata JSONB,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP,

    created_by VARCHAR(255),

    updated_by VARCHAR(255),

    version BIGINT,

    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_document_embeddings_project
        FOREIGN KEY (project_id)
            REFERENCES projects(id)
);