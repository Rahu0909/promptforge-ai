ALTER TABLE messages
    ADD COLUMN provider VARCHAR(50);

ALTER TABLE messages
    ADD COLUMN model VARCHAR(100);

ALTER TABLE messages
    ADD COLUMN finish_reason VARCHAR(50);