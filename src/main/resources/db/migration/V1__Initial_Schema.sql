-- Initial schema
CREATE TABLE auth_users
(
    id UUID PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    account_status VARCHAR(30) NOT NULL,
    auth_provider VARCHAR(30) NOT NULL,
    email_verified BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_auth_user_email
    ON auth_users(email);

CREATE INDEX idx_auth_user_status
    ON auth_users(account_status);