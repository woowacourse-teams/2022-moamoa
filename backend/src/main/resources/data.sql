INSERT INTO study(id, title, excerpt, thumbnail, status, owner)
VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'OPEN', 'greenlawn');

INSERT INTO study(id, title, excerpt, thumbnail, status, owner)
VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'OPEN', 'dwoo');

INSERT INTO study(id, title, excerpt, thumbnail, status, owner)
VALUES (3, 'javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'OPEN', 'verus');

INSERT INTO study(id, title, excerpt, thumbnail, status, owner)
VALUES (4, 'HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'CLOSE', 'jjanggu');

INSERT INTO study(id, title, excerpt, thumbnail, status, owner)
VALUES (5, '알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'CLOSE', 'whitedog');

INSERT INTO category(id, name) VALUES (1, 'GENERATION');
INSERT INTO category(id, name) VALUES (2, 'AREA');
INSERT INTO category(id, name) VALUES (3, 'SUBJECT');

INSERT INTO tag(id, name, description, category_id) VALUES (1, 'Java', '자바', 3);
INSERT INTO tag(id, name, description, category_id) VALUES (2, '4기', '우테코4기', 1);
INSERT INTO tag(id, name, description, category_id) VALUES (3, 'BE', '백엔드', 2);
INSERT INTO tag(id, name, description, category_id) VALUES (4, 'FE', '프론트엔드', 2);
INSERT INTO tag(id, name, description, category_id) VALUES (5, 'React', '리액트', 3);

INSERT INTO study_tag(study_id, tag_id) VALUES (1, 1);
INSERT INTO study_tag(study_id, tag_id) VALUES (1, 2);
INSERT INTO study_tag(study_id, tag_id) VALUES (1, 3);

INSERT INTO study_tag(study_id, tag_id) VALUES (2, 2);
INSERT INTO study_tag(study_id, tag_id) VALUES (2, 4);
INSERT INTO study_tag(study_id, tag_id) VALUES (2, 5);


INSERT INTO study_tag(study_id, tag_id) VALUES (3, 2);
INSERT INTO study_tag(study_id, tag_id) VALUES (3, 4);

INSERT INTO study_tag(study_id, tag_id) VALUES (4, 2);
INSERT INTO study_tag(study_id, tag_id) VALUES (4, 3);

INSERT INTO study_tag(study_id, tag_id) VALUES (5, 2);
INSERT INTO study_tag(study_id, tag_id) VALUES (5, 3);
>>>>>>> 9400ca3b287ae5ecbe4fbb59b0bad3012f545004
