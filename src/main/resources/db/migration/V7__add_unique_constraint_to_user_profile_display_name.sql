ALTER TABLE user_profiles
    ADD CONSTRAINT uk_user_profile_display_name
        UNIQUE(display_name);