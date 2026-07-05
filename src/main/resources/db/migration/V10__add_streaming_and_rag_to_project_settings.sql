ALTER TABLE project_settings
    ADD COLUMN streaming_enabled BOOLEAN NOT NULL DEFAULT TRUE;

ALTER TABLE project_settings
    ADD COLUMN rag_enabled BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE project_settings
    ADD COLUMN memory_enabled BOOLEAN NOT NULL DEFAULT TRUE;