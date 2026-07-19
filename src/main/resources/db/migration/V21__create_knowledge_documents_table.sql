CREATE TABLE knowledge_documents
(
    id                  UUID PRIMARY KEY,

    project_id          UUID         NOT NULL,

    document_name       VARCHAR(255) NOT NULL,

    original_file_name  VARCHAR(255) NOT NULL,

    document_type       VARCHAR(50)  NOT NULL,

    status              VARCHAR(50)  NOT NULL,

    total_chunks        INTEGER      NOT NULL DEFAULT 0,

    processed_at        TIMESTAMP,

    storage_path        TEXT         NOT NULL,

    created_at          TIMESTAMP    NOT NULL,

    updated_at          TIMESTAMP,

    created_by          UUID,

    updated_by          UUID,

    is_deleted          BOOLEAN      NOT NULL DEFAULT FALSE,

    version             BIGINT       NOT NULL DEFAULT 0,

    CONSTRAINT fk_knowledge_document_project
        FOREIGN KEY (project_id)
            REFERENCES projects (id)
);