package com.woowacourse.moamoa.member.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, status, description, current_member_count, max_member_count, created_at, start_date, owner_id) VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'OPEN', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '2021-11-08T11:58:20.551705', '2021-12-08T11:58:20.657123', 2)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 1)");
    }

    @DisplayName("스터디에 참여한 회원이라면 true를 반환한다.")
    @Test
    void returnTrueMemberParticipatingInTheStudy() {
        assertThat(memberDao.existsByStudyIdAndMemberId(1L, 1L)).isTrue();
    }

    @DisplayName("스터디에 참여하지 않은 회원이라면 false를 반환한다.")
    @Test
    void returnTrueMemberNotParticipatedInTheStudy() {
        assertThat(memberDao.existsByStudyIdAndMemberId(1L, 2L)).isFalse();
    }
}
