CREATE TABLE prompt_versions
(
    id UUID PRIMARY KEY,

    prompt_id UUID NOT NULL,

    version_number INTEGER NOT NULL,

    title VARCHAR(150) NOT NULL,

    description VARCHAR(1000),

    content TEXT NOT NULL,

    change_summary VARCHAR(500),

    created_at TIMESTAMPTZ NOT NULL,

    updated_at TIMESTAMPTZ NOT NULL,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_prompt_version_prompt
        FOREIGN KEY(prompt_id)
            REFERENCES prompts(id),

    CONSTRAINT uk_prompt_version
        UNIQUE(prompt_id, version_number)
);

CREATE INDEX idx_prompt_version_prompt
    ON prompt_versions(prompt_id);