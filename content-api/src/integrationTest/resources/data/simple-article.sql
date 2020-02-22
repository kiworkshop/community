INSERT INTO simple_article(id, title, description, content, created_at, updated_at) VALUES
(1, 'title', 'description', 'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00'),
(2, 'title', 'description', 'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00'),
(3, 'title', 'description', 'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00'),
(4, 'title', 'description' ,'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00'),
(5, 'title', 'description' ,'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00'),
(6, 'title', 'description' ,'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00'),
(7, 'title', 'description' ,'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00'),
(8, 'title', 'description' ,'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00'),
(9, 'title', 'description' ,'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00'),
(10, 'title', 'description' ,'content', '2019-03-26 09:00:00', '2019-03-26 09:00:00');

INSERT INTO simple_tag(id, name) VALUES
(1, 'tag1');

INSERT INTO simple_article_simple_tag(simple_article_id, simple_tag_id) VALUES
 (1, 1);

