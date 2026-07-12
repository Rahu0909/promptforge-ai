CREATE TABLE conversations
(
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL,
    title VARCHAR(150) NOT NULL,
    provider VARCHAR(50) NOT NULL,
    model VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL,
    last_message_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL,

    CONSTRAINT fk_conversation_project
        FOREIGN KEY(project_id)
            REFERENCES projects(id)
);

CREATE INDEX idx_conversation_project
    ON conversations(project_id);

CREATE INDEX idx_conversation_status
    ON conversations(status);

CREATE INDEX idx_conversation_last_message
    ON conversations(last_message_at);



CREATE TABLE messages
(
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL,
    role VARCHAR(20) NOT NULL,
    content TEXT NOT NULL,
    token_count INTEGER,
    generation_time_ms BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL,

    CONSTRAINT fk_message_conversation
        FOREIGN KEY(conversation_id)
            REFERENCES conversations(id)
);

CREATE INDEX idx_message_conversation
    ON messages(conversation_id);

CREATE INDEX idx_message_role
    ON messages(role);