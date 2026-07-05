CREATE TABLE projects
(
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(2000),
    visibility VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL,
    CONSTRAINT fk_project_owner
        FOREIGN KEY (owner_id)
            REFERENCES user_profiles(id)
);

CREATE INDEX idx_project_owner
    ON projects(owner_id);

CREATE INDEX idx_project_status
    ON projects(status);

CREATE INDEX idx_project_visibility
    ON projects(visibility);

CREATE UNIQUE INDEX uk_project_owner_name
    ON projects(owner_id, LOWER(name));


CREATE TABLE project_settings
(
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL UNIQUE,
    provider VARCHAR(30) NOT NULL,
    model VARCHAR(100) NOT NULL,
    temperature DOUBLE PRECISION NOT NULL,
    max_tokens INTEGER NOT NULL,
    system_prompt TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL,
    CONSTRAINT fk_project_settings_project
        FOREIGN KEY (project_id)
            REFERENCES projects(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_project_settings_project
    ON project_settings(project_id);