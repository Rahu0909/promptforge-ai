CREATE TABLE user_preferences
(
    id UUID PRIMARY KEY,
    user_profile_id UUID NOT NULL UNIQUE,
    theme VARCHAR(20) NOT NULL,
    preferred_ai_provider VARCHAR(20) NOT NULL,
    streaming_enabled BOOLEAN NOT NULL,
    email_notifications BOOLEAN NOT NULL,
    prompt_auto_save BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL,
    CONSTRAINT fk_user_preference_profile
        FOREIGN KEY(user_profile_id)
            REFERENCES user_profiles(id)
            ON DELETE CASCADE
);