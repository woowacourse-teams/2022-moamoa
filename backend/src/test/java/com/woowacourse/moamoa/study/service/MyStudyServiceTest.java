package com.woowacourse.moamoa.study.service;

import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import java.time.LocalDateTime;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import com.woowacourse.moamoa.study.service.response.MyStudyResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class MyStudyServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MyStudyDao myStudyDao;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    private MyStudyService myStudyService;

    @BeforeEach
    void setUp() {
        myStudyService = new MyStudyService(myStudyDao, memberRepository, studyRepository);

        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (1, 1, 'jjanggu', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (2, 2, 'greenlawn', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (3, 3, 'dwoo', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (4, 4, 'verus', 'https://image', 'github.com')");

        final LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_at, start_date, owner_id) "
                + "VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'RECRUITMENT_START', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '" + now + "', '2021-12-08', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_at, enrollment_end_date, start_date, end_date, owner_id) "
                + "VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'RECRUITMENT_START', 'PREPARE', '디우의 뤼액트 스터디입니다.', 4, 5, '" + now + "', '2021-11-09', '2021-11-10', '2021-12-08', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_at, start_date, owner_id) "
                + "VALUES (3, 'javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'RECRUITMENT_START', 'PREPARE', '그린론의 자바스크립트 접해보기', 3, 20, '" + now + "', '2022-08-03', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, max_member_count, created_at, start_date, owner_id) "
                + "VALUES (4, 'HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'RECRUITMENT_END', 'PREPARE', '디우의 HTTP 정복하기', 5, '" + now + "', '2022-08-03', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, created_at, owner_id, start_date) "
                + "VALUES (5, '알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'RECRUITMENT_END', 'PREPARE', '알고리즘을 TDD로 풀자의 베루스입니다.', 1, '" + now + "', 4, '2021-12-06')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, created_at, owner_id, start_date, enrollment_end_date, end_date) "
                + "VALUES (6, 'Linux 스터디', '리눅스 설명', 'linux thumbnail', 'RECRUITMENT_END', 'PREPARE', 'Linux를 공부하자의 베루스입니다.', 1, '" + now + "', 4, '2021-12-06', '2021-12-07', '2022-01-07')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_at, owner_id, start_date, enrollment_end_date, end_date) "
                + "VALUES (7, 'OS 스터디', 'OS 설명', 'os thumbnail', 'RECRUITMENT_END', 'PREPARE', 'OS를 공부하자의 베루스입니다.', 1, 6, '" + now + "', 4, '2021-12-06', '2021-12-07', '2022-01-07')");

        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (1, 'generation')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (2, 'area')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (3, 'subject')");

        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (1, 'Java', '자바', 3)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (2, '4기', '우테코4기', 1)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (3, 'BE', '백엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (4, 'FE', '프론트엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (5, 'React', '리액트', 3)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (1, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (1, 3)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (2, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (2, 4)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (2, 5)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (3, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (3, 4)");

        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (4, 2)");
        jdbcTemplate.update("INSERT INTO study_tag(study_id, tag_id) VALUES (4, 3)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 3)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 4)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 2)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 4)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (3, 3)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (3, 4)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (7, 1)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (7, 2)");
    }

    @DisplayName("내가 참여한 스터디를 조회한다.")
    @Test
    void findMyStudies() {
        final MyStudiesResponse myStudiesResponse = myStudyService.getStudies(2L);

        final List<MemberData> owners = myStudiesResponse.getStudies()
                .stream()
                .map(MyStudyResponse::getOwner)
                .collect(toList());

        final List<List<TagSummaryData>> tags = myStudiesResponse.getStudies()
                .stream()
                .map(MyStudyResponse::getTags)
                .collect(toList());

        final List<MyStudyResponse> studies = myStudiesResponse.getStudies();

        assertThat(studies)
                .hasSize(4)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "studyStatus", "currentMemberCount", "maxMemberCount")
                .contains(
                        tuple("Java 스터디", PREPARE, 3, 10),
                        tuple("javaScript 스터디" ,PREPARE, 3, 20),
                        tuple("React 스터디", PREPARE, 4, 5),
                        tuple("OS 스터디", PREPARE, 1, 6)
                );

        assertThat(owners)
                .hasSize(4)
                .extracting("githubId", "username", "imageUrl", "profileUrl")
                .contains(
                        tuple(2L, "greenlawn", "https://image", "github.com"),
                        tuple(2L, "greenlawn", "https://image", "github.com"),
                        tuple(3L, "dwoo", "https://image", "github.com"),
                        tuple(4L, "verus", "https://image", "github.com")
                );

        assertThat(tags).hasSize(4);
    }

    @DisplayName("태그가 없는 스터디를 조회한다.")
    @Test
    void findMyStudiesWithoutTags() {
        final MyStudiesResponse myStudiesResponse = myStudyService.getStudies(1L);

        final List<MemberData> owners = myStudiesResponse.getStudies()
                .stream()
                .map(MyStudyResponse::getOwner)
                .collect(toList());

        final List<List<TagSummaryData>> tags = myStudiesResponse.getStudies()
                .stream()
                .map(MyStudyResponse::getTags)
                .collect(toList());

        final List<MyStudyResponse> studies = myStudiesResponse.getStudies();

        assertThat(studies)
                .hasSize(1)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "studyStatus", "currentMemberCount", "maxMemberCount")
                .contains(
                        tuple("OS 스터디", PREPARE, 1, 6)
                );

        assertThat(owners)
                .hasSize(1)
                .extracting("githubId", "username", "imageUrl", "profileUrl")
                .contains(
                        tuple(4L, "verus", "https://image", "github.com")
                );

        assertThat(tags.get(0).size()).isZero();
    }

    @DisplayName("존재하지 않은 내가 참여한 스터디 조회 시 예외 발생")
    @Test
    void getMyStudyNotExistUser() {
        assertThatThrownBy(() -> myStudyService.getStudies(5L))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원을 찾을 수 없습니다.");
    }

    @DisplayName("사용자 역할 조회하는 기능에서 존재하지 않는 사용자 조회 시 예외 발생")
    @Test
    void getMemberRoleNotExistUser() {
        assertThatThrownBy(() -> myStudyService.findMyRoleInStudy(5L, 1L))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원을 찾을 수 없습니다.");
    }

    @DisplayName("사용자 역할 조회하는 기능에서 존재하지 않는 스터디 조회 시 예외 발생")
    @Test
    void getMemberRoleNotExistStudy() {
        assertThatThrownBy(() -> myStudyService.findMyRoleInStudy(1L, 10L))
                .isInstanceOf(StudyNotFoundException.class)
                .hasMessageContaining("스터디가 존재하지 않습니다.");
    }
}
