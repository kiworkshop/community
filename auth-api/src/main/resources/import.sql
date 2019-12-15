INSERT INTO authority(id, role) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');
INSERT INTO user(id, username, `password`, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES (1, '123e4567-e89b-12d3-a456-426655440000', '{bcrypt}$2a$10$uQDxKRlFuDw2rbPWFO3TK.cYclk3UUBUsbXtWzB4eMuNpyGl6rztS', true, true, true, true);
INSERT INTO user_authorities(user_id, authorities_id) VALUES (1, 1), (1, 2);
