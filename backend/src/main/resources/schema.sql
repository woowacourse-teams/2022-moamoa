CREATE TABLE IF NOT EXISTS member
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    github_id   BIGINT       NOT NULL UNIQUE,
    username    VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE,
    image_url   VARCHAR(255),
    profile_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS study
(
    id                   BIGINT PRIMARY KEY AUTO_INCREMENT,
    title                VARCHAR(30)  NOT NULL,
    excerpt              VARCHAR(50)  NOT NULL,
    thumbnail            VARCHAR(255) NOT NULL,
    recruitment_status   VARCHAR(255) NOT NULL,
    study_status         VARCHAR(255) NOT NULL,
    description          MEDIUMTEXT,
    current_member_count INTEGER DEFAULT 1,
    max_member_count     INTEGER,
    created_at           DATETIME     not null,
    enrollment_end_date  DATE,
    start_date           DATE         not null,
    end_date             DATE,
    owner_id             BIGINT       NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES member (id)
);

CREATE TABLE IF NOT EXISTS review
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    study_id           BIGINT  NOT NULL,
    member_id          BIGINT  NOT NULL,
    content            MEDIUMTEXT,
    created_date       DATETIME    not null,
    last_modified_date DATETIME    not null,
    deleted            boolean not null,
    FOREIGN KEY (study_id) REFERENCES study (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE IF NOT EXISTS link
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    study_id           BIGINT   NOT NULL,
    author_id          BIGINT   NOT NULL,
    link_url           MEDIUMTEXT NOT NULL,
    description        MEDIUMTEXT,
    created_date       DATETIME NOT NULL,
    last_modified_date DATETIME NOT NULL,
    deleted            boolean  NOT NULL,
    FOREIGN KEY (study_id) REFERENCES study (id),
    FOREIGN KEY (author_id) REFERENCES member (id)
);

CREATE TABLE IF NOT EXISTS category
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tag
(
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE IF NOT EXISTS study_tag
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    study_id BIGINT,
    tag_id   BIGINT,
    FOREIGN KEY (study_id) REFERENCES study (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);

CREATE TABLE IF NOT EXISTS study_member
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    study_id           BIGINT,
    member_id          BIGINT,
    participation_date DATE not null,
    FOREIGN KEY (study_id) REFERENCES study (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE IF NOT EXISTS article
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    title              VARCHAR(255) NOT NULL,
    content            MEDIUMTEXT     NOT NULL,
    author_id          BIGINT,
    study_id           BIGINT,
    created_date       DATETIME     NOT NULL,
    last_modified_date DATETIME     NOT NULL,
    type               VARCHAR(255) NOT NULL,
    deleted            boolean      NOT NULL,
    FOREIGN KEY (author_id) REFERENCES member (id),
    FOREIGN KEY (study_id) REFERENCES study (id)
);

CREATE TABLE IF NOT EXISTS comment
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    author_id          BIGINT,
    article_id         BIGINT,
    content            MEDIUMTEXT NOT NULL,
    created_date       DATETIME NOT NULL,
    last_modified_date DATETIME NOT NULL,
    FOREIGN KEY (author_id) REFERENCES member (id),
    FOREIGN KEY (article_id) REFERENCES article (id)
);

CREATE TABLE IF NOT EXISTS temp_article
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    title              VARCHAR(255) NOT NULL,
    content            MEDIUMTEXT   NOT NULL,
    author_id          BIGINT,
    study_id           BIGINT,
    created_date       DATETIME     not null,
    last_modified_date DATETIME     not null,
    type               VARCHAR(255) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES member (id),
    FOREIGN KEY (study_id) REFERENCES study (id)
);
