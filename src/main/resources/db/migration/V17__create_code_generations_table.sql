CREATE TABLE code_generations
(
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL,
    prompt TEXT NOT NULL,
    generation_type VARCHAR(50) NOT NULL,
    language VARCHAR(50),
    framework VARCHAR(100),
    generated_code TEXT NOT NULL,
    status VARCHAR(30) NOT NULL,
    provider VARCHAR(30) NOT NULL,
    model VARCHAR(100) NOT NULL,
    token_count INTEGER NOT NULL,
    generation_time_ms BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL,
    CONSTRAINT fk_codegen_project
        FOREIGN KEY (project_id)
            REFERENCES projects(id)
);

CREATE INDEX idx_codegen_project
    ON code_generations(project_id);

CREATE INDEX idx_codegen_type
    ON code_generations(generation_type);

CREATE INDEX idx_codegen_status
    ON code_generations(status);

CREATE INDEX idx_codegen_created
    ON code_generations(created_at DESC);