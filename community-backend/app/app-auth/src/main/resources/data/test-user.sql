INSERT INTO user(id, username, social_provider, `social_id`, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES (1, '123e4567-e89b-12d3-a456-426655440000', 'GOOGLE', '12345678', true, true, true, true);
INSERT INTO user_authorities(user_id, authorities_id) VALUES (1, 1), (1, 2);
