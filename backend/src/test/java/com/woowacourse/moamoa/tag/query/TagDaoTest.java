package com.woowacourse.moamoa.tag.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.tag.query.response.TagData;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDataBase() {
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (1, 1, 'jjanggu', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (2, 2, 'greenlawn', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (3, 3, 'dwoo', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (4, 4, 'verus', 'https://image', 'github.com')");

        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_at, start_date, owner_id) VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'OPEN', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '2021-11-08T11:58:20.551705', '2021-12-08T11:58:20.657123', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_at, enrollment_end_date, start_date, end_date, owner_id) VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'OPEN', 'PREPARE', '디우의 뤼액트 스터디입니다.', 4, 5, '2021-11-08T11:58:20.551705', '2021-11-09T11:58:20.551705', '2021-11-10T11:58:20.551705', '2021-12-08T11:58:20.551705', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_at, owner_id) VALUES (3, 'javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'OPEN', 'PREPARE', '그린론의 자바스크립트 접해보기', 3, 20, '2021-11-08T11:58:20.551705', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, max_member_count, created_at, owner_id) VALUES (4, 'HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'CLOSE', 'PREPARE', '디우의 HTTP 정복하기', 5, '2021-11-08T11:58:20.551705', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, created_at, owner_id, start_date) VALUES (5, '알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'CLOSE', 'PREPARE', '알고리즘을 TDD로 풀자의 베루스입니다.', 1, '2021-11-08T11:58:20.551705', 4, '2021-12-06T11:56:32.123567')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, created_at, owner_id, start_date, enrollment_end_date, end_date) VALUES (6, 'Linux 스터디', '리눅스 설명', 'linux thumbnail', 'CLOSE', 'PREPARE', 'Linux를 공부하자의 베루스입니다.', 1, '2021-11-08T11:58:20.551705', 4, '2021-12-06T11:56:32.123567', '2021-12-07T11:56:32.123567', '2022-01-07T11:56:32.123567')");

        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (1, 'generation')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (2, 'area')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (3, 'subject')");

        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (1, 'Java', '자바', 3)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (2, '4기', '우테코4기', 1)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (3, 'BE', '백엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (4, 'FE', '프론트엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (5, 'React', '리액트', 3)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (1, 1)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (1, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (1, 3)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (2, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (2, 4)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (2, 5)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (3, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (3, 4)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (4, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (4, 3)");
    }

    @DisplayName("필터 없이 조회시 태그 목록 전체를 조회한다.")
    @Test
    void findAllByBlankTagName() {
        List<TagData> tagData = tagDao.searchByShortNameAndCategoryId("", Optional.empty());

        assertThat(tagData)
                .hasSize(5)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(2L, "4기", "우테코4기", 1L, "generation"),
                        tuple(3L, "BE", "백엔드", 2L, "area"),
                        tuple(4L, "FE", "프론트엔드", 2L, "area"),
                        tuple(5L, "React", "리액트", 3L, "subject")
                );
    }

    @DisplayName("대소문자 구분없이 태그 이름으로 조회한다.")
    @Test
    void findAllByNameContainingIgnoreCase() {
        List<TagData> tagData = tagDao.searchByShortNameAndCategoryId("ja", Optional.empty());

        assertThat(tagData)
                .hasSize(1)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject")
                );
    }

    @DisplayName("카테고리로 태그를 조회한다.")
    @Test
    void findAllByCategory() {
        List<TagData> tagData = tagDao.searchByShortNameAndCategoryId("", Optional.of(3L));

        assertThat(tagData)
                .hasSize(2)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(5L, "React", "리액트", 3L, "subject")
                );
    }

    @DisplayName("카테고리와 이름으로 태그를 조회한다.")
    @Test
    void findAllByCategoryAndName() {
        List<TagData> tagData = tagDao.searchByShortNameAndCategoryId("ja", Optional.of(3L));

        assertThat(tagData)
                .hasSize(1)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject")
                );
    }

    @DisplayName("스터디에 부여된 태그 목록을 조회한다.")
    @Test
    void getAttachedTagsByStudyId() {
        // Java 스터디에 부착된 태그 : Java, 4기, BE
        final List<TagData> attachedTags = tagDao.findTagsByStudyId(1L);

        assertThat(attachedTags)
                .hasSize(3)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactlyInAnyOrder(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(2L, "4기", "우테코4기", 1L, "generation"),
                        tuple(3L, "BE", "백엔드", 2L, "area")
                );
    }

    @DisplayName("스터디에 부여된 태그가 없는 경우 빈 목록을 조회한다.")
    @Test
    void getEmptyAttachedTagsByStudyId() {
        final List<TagData> attachedTags = tagDao.findTagsByStudyId(6L);

        assertThat(attachedTags).isEmpty();
    }
}