-- PostgreSQL does not index foreign key columns automatically.

CREATE INDEX idx_chat_messages_room_created ON chat_messages (chat_room_id, created_at DESC) WHERE is_deleted = false;
CREATE INDEX idx_user_articles_user_created ON user_articles (user_id, created_at DESC) WHERE is_deleted = false;
CREATE INDEX idx_group_articles_group ON group_articles (group_id);
CREATE INDEX idx_group_tasks_group ON group_tasks (group_id);
CREATE INDEX idx_submissions_task_user ON group_task_submissions (group_task_id, user_id);
CREATE INDEX idx_chat_rooms_group ON chat_rooms (group_id);
CREATE INDEX idx_groups_topic ON groups (group_topic_id);
CREATE INDEX idx_accounts_role ON accounts (role_id);

-- The primary key is (permission_id, role_id), so loading a role's permissions by role_id cannot use it
CREATE INDEX idx_role_permissions_role ON role_permissions (role_id);
