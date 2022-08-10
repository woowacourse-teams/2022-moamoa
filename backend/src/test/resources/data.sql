INSERT INTO category(id, name) VALUES (1, 'generation');
INSERT INTO category(id, name) VALUES (2, 'area');
INSERT INTO category(id, name) VALUES (3, 'subject');

INSERT INTO tag(id, name, description, category_id) VALUES (1, 'Java', '자바', 3);
INSERT INTO tag(id, name, description, category_id) VALUES (2, '4기', '우테코4기', 1);
INSERT INTO tag(id, name, description, category_id) VALUES (3, 'BE', '백엔드', 2);
INSERT INTO tag(id, name, description, category_id) VALUES (4, 'FE', '프론트엔드', 2);
INSERT INTO tag(id, name, description, category_id) VALUES (5, 'React', '리액트', 3);
