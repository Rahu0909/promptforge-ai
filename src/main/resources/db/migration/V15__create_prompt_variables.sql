CREATE TABLE prompt_variables
(
    id UUID PRIMARY KEY,

    prompt_id UUID NOT NULL,

    variable_name VARCHAR(100) NOT NULL,

    description VARCHAR(500),

    default_value VARCHAR(1000),

    required BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL,

    updated_at TIMESTAMPTZ NOT NULL,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_prompt_variable_prompt
        FOREIGN KEY(prompt_id)
            REFERENCES prompts(id),

    CONSTRAINT uk_prompt_variable
        UNIQUE(prompt_id, variable_name)
);

CREATE INDEX idx_prompt_variable_prompt
    ON prompt_variables(prompt_id);