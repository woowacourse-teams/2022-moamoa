DROP TABLE IF EXISTS study_tag;
DROP TABLE IF EXISTS study_member;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS study;
DROP TABLE IF EXISTS member;

CREATE TABLE member
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    github_id BIGINT NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    image_url VARCHAR(255),
    profile_url VARCHAR(255)
);

CREATE TABLE study
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(30) NOT NULL,
    excerpt VARCHAR(50) NOT NULL,
    thumbnail VARCHAR(255) NOT NULL,
    recruitment_status VARCHAR(255) NOT NULL,
    study_status VARCHAR(255) NOT NULL,
    description MEDIUMTEXT,
    current_member_count INTEGER DEFAULT 1,
    max_member_count INTEGER,
    created_date DATETIME not null,
    last_modified_date DATETIME  not null,
    enrollment_end_date DATE,
    start_date DATE,
    end_date DATE,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES member (id)
);

CREATE TABLE review
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    study_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    content MEDIUMTEXT,
    created_date DATETIME not null,
    last_modified_date DATETIME  not null,
    FOREIGN KEY (study_id) REFERENCES study (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
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

CREATE TABLE study_member
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    study_id BIGINT,
    member_id BIGINT,
    FOREIGN KEY (study_id) REFERENCES study (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
);
