INSERT INTO prompt_categories
(id, name, description, created_at, updated_at, deleted, version)
VALUES
    (gen_random_uuid(), 'Backend', 'Spring Boot, Java and backend prompts', now(), now(), false, 0),

    (gen_random_uuid(), 'Frontend', 'React, Angular and frontend prompts', now(), now(), false, 0),

    (gen_random_uuid(), 'SQL', 'Database schema and SQL generation prompts', now(), now(), false, 0),

    (gen_random_uuid(), 'Docker', 'Docker and Docker Compose prompts', now(), now(), false, 0),

    (gen_random_uuid(), 'Testing', 'JUnit and testing prompts', now(), now(), false, 0),

    (gen_random_uuid(), 'DevOps', 'CI/CD and deployment prompts', now(), now(), false, 0),

    (gen_random_uuid(), 'General', 'General AI prompts', now(), now(), false, 0);