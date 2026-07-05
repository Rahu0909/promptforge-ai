ALTER TABLE user_profiles
    ADD COLUMN status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE';

CREATE INDEX idx_user_profile_status
    ON user_profiles(status);