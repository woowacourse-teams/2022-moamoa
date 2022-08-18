package com.woowacourse.moamoa.member.query;

import com.woowacourse.moamoa.common.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class MemberDaoTest {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (1, 1, 'jjanggu', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (2, 2, 'greenlawn', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, status, description, current_member_count, max_member_count, created_date, start_date, owner_id) VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'OPEN', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '2021-11-08T11:58:20.551705', '2021-12-08T11:58:20.657123', 2)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 1)");
    }
}
