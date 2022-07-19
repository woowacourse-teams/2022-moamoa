DROP TABLE IF EXISTS study_filter;
DROP TABLE IF EXISTS study_member;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS study;

CREATE TABLE study
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    excerpt VARCHAR(255) NOT NULL,
    thumbnail VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    description MEDIUMTEXT,
    current_member_count INTEGER DEFAULT 1,
    max_member_count INTEGER,
    deadline DATETIME,
    start_date DATETIME,
    end_date DATETIME,
    owner VARCHAR(255) NOT NULL
);

CREATE TABLE category
(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE tag
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE study_tag
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    study_id BIGINT,
    tag_id BIGINT,
    FOREIGN KEY (study_id) REFERENCES study (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);

CREATE TABLE member
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    github_id BIGINT NOT NULL,
    username VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    profile_url VARCHAR(255)
);

CREATE TABLE study_member
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    study_id BIGINT,
    member_id BIGINT,
    FOREIGN KEY (study_id) REFERENCES study (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
);
