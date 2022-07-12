DROP TABLE IF EXISTS study;

CREATE TABLE study
(
    id bigint primary key auto_increment,
    title varchar(255) not null,
    description varchar(255) not null,
    thumbnail varchar(255) not null,
    status varchar(255) not null
);

INSERT INTO study(title, description, thumbnail, status)
VALUES ('Java 스터디', '자바 설명', 'java thumbnail', 'OPEN');

INSERT INTO study(title, description, thumbnail, status)
VALUES ('React 스터디', '리액트 설명', 'react thumbnail', 'OPEN');

INSERT INTO study(title, description, thumbnail, status)
VALUES ('javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'OPEN');

INSERT INTO study(title, description, thumbnail, status)
VALUES ('HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'CLOSE');

INSERT INTO study(title, description, thumbnail, status)
VALUES ('알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'CLOSE');
