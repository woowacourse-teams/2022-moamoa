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

INSERT INTO category(id, name) VALUES (1, 'generation');
INSERT INTO category(id, name) VALUES (2, 'area');
INSERT INTO category(id, name) VALUES (3, 'tag');

INSERT INTO filter(id, name, category_id) VALUES (1, 'Java', 3);
INSERT INTO filter(id, name, category_id) VALUES (2, '4기', 1);
INSERT INTO filter(id, name, category_id) VALUES (3, 'BE', 2);
INSERT INTO filter(id, name, category_id) VALUES (4, 'FE', 2);
INSERT INTO filter(id, name, category_id) VALUES (5, 'React', 3);

INSERT INTO study_filter(study_id, filter_id) VALUES (1, 1);
INSERT INTO study_filter(study_id, filter_id) VALUES (1, 2);
INSERT INTO study_filter(study_id, filter_id) VALUES (1, 3);

INSERT INTO study_filter(study_id, filter_id) VALUES (2, 2);
INSERT INTO study_filter(study_id, filter_id) VALUES (2, 4);
INSERT INTO study_filter(study_id, filter_id) VALUES (2, 5);


INSERT INTO study_filter(study_id, filter_id) VALUES (3, 2);
INSERT INTO study_filter(study_id, filter_id) VALUES (3, 4);

INSERT INTO study_filter(study_id, filter_id) VALUES (4, 2);
INSERT INTO study_filter(study_id, filter_id) VALUES (4, 3);

INSERT INTO study_filter(study_id, filter_id) VALUES (5, 2);
INSERT INTO study_filter(study_id, filter_id) VALUES (5, 3);
