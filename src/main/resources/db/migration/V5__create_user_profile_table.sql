CREATE TABLE user_profiles
(
    id UUID PRIMARY KEY,
    auth_user_id UUID NOT NULL UNIQUE,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    display_name VARCHAR(120) NOT NULL,
    bio VARCHAR(500),
    avatar_url VARCHAR(500),
    timezone VARCHAR(80) NOT NULL,
    language VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL,
    CONSTRAINT fk_user_profile_auth_user
        FOREIGN KEY(auth_user_id)
            REFERENCES auth_users(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_user_profile_display_name
    ON user_profiles(display_name);