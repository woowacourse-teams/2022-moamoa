INSERT INTO study(title, excerpt, thumbnail, status, owner)
VALUES ('Java 스터디', '자바 설명', 'java thumbnail', 'OPEN', 'greenlawn');

INSERT INTO study(title, excerpt, thumbnail, status, owner)
VALUES ('React 스터디', '리액트 설명', 'react thumbnail', 'OPEN', 'dwoo');

INSERT INTO study(title, excerpt, thumbnail, status, owner)
VALUES ('javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'OPEN', 'verus');

INSERT INTO study(title, excerpt, thumbnail, status, owner)
VALUES ('HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'CLOSE', 'jjanggu');

INSERT INTO study(title, excerpt, thumbnail, status, owner)
VALUES ('알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'CLOSE', 'whitedog');

INSERT INTO category(id, name) VALUES (1, 'GENERATION');
INSERT INTO category(id, name) VALUES (2, 'AREA');
INSERT INTO category(id, name) VALUES (3, 'SUBJECT');

INSERT INTO tag(name, description, category_id) VALUES ('Java', '자바', 3);
INSERT INTO tag(name, description, category_id) VALUES ('4기', '우테코4기', 1);
INSERT INTO tag(name, description, category_id) VALUES ('BE', '백엔드', 2);
INSERT INTO tag(name, description, category_id) VALUES ('FE', '프론트엔드', 2);
INSERT INTO tag(name, description, category_id) VALUES ('React', '리액트', 3);
