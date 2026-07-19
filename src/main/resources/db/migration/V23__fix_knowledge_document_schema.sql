-- created_by / updated_by should be VARCHAR

ALTER TABLE knowledge_documents
ALTER COLUMN created_by TYPE VARCHAR(100)
USING created_by::text;

ALTER TABLE knowledge_documents
ALTER COLUMN updated_by TYPE VARCHAR(100)
USING updated_by::text;

-- timestamps should match OffsetDateTime

ALTER TABLE knowledge_documents
ALTER COLUMN created_at
TYPE TIMESTAMP WITH TIME ZONE;

ALTER TABLE knowledge_documents
ALTER COLUMN updated_at
TYPE TIMESTAMP WITH TIME ZONE;

ALTER TABLE knowledge_documents
ALTER COLUMN processed_at
TYPE TIMESTAMP WITH TIME ZONE;

-- remove obsolete column

ALTER TABLE knowledge_documents
DROP COLUMN IF EXISTS is_deleted;