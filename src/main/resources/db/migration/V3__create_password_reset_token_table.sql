CREATE TABLE auth_password_reset_tokens
(
    id UUID PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    auth_user_id UUID NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL,
    CONSTRAINT fk_password_reset_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_password_reset_token
    ON auth_password_reset_tokens(token);

CREATE INDEX idx_password_reset_user
    ON auth_password_reset_tokens(auth_user_id);