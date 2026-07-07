CREATE TABLE prompts
(
    id UUID PRIMARY KEY,

    project_id UUID NOT NULL,

    category_id UUID NOT NULL,

    title VARCHAR(150) NOT NULL,

    description VARCHAR(1000),

    content TEXT NOT NULL,

    status VARCHAR(30) NOT NULL,

    visibility VARCHAR(30) NOT NULL,

    favorite BOOLEAN NOT NULL DEFAULT FALSE,

    latest_version INTEGER NOT NULL DEFAULT 1,

    created_at TIMESTAMPTZ NOT NULL,

    updated_at TIMESTAMPTZ NOT NULL,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_prompt_project
        FOREIGN KEY (project_id)
            REFERENCES projects(id),

    CONSTRAINT fk_prompt_category
        FOREIGN KEY (category_id)
            REFERENCES prompt_categories(id),

    CONSTRAINT uk_prompt_project_title
        UNIQUE(project_id, title)
);

CREATE INDEX idx_prompt_project
    ON prompts(project_id);

CREATE INDEX idx_prompt_category
    ON prompts(category_id);

CREATE INDEX idx_prompt_status
    ON prompts(status);

CREATE INDEX idx_prompt_title
    ON prompts(title);