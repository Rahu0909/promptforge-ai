CREATE TABLE auth_refresh_tokens
(
    id UUID PRIMARY KEY,
    token VARCHAR(512) NOT NULL UNIQUE,
    auth_user_id UUID NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    device_name VARCHAR(100),
    ip_address VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL,
    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (auth_user_id)
            REFERENCES auth_users(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token_user
    ON auth_refresh_tokens(auth_user_id);

CREATE INDEX idx_refresh_token_revoked
    ON auth_refresh_tokens(revoked);

CREATE INDEX idx_refresh_token_expiry
    ON auth_refresh_tokens(expires_at);