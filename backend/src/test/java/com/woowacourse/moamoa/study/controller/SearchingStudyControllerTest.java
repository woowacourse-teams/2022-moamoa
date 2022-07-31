package com.woowacourse.moamoa.study.controller;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.StudyDetailsDao;
import com.woowacourse.moamoa.study.query.StudySummaryDao;
import com.woowacourse.moamoa.study.query.data.StudyDetailsData;
import com.woowacourse.moamoa.study.service.SearchingStudyService;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import com.woowacourse.moamoa.tag.query.TagDao;
import com.woowacourse.moamoa.tag.query.response.TagData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
public class SearchingStudyControllerTest {

    private static final Function<Object, String> NULL_TO_EMPTY_STRING_CONVERTER = value -> value == null ? "" : value.toString();

    private SearchingStudyController sut;

    @Autowired
    private StudySummaryDao studySummaryDao;

    @Autowired
    private StudyDetailsDao studyDetailsDao;

    @Autowired
    private MemberDao memberDao;

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

        final LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, start_date, owner_id) "
                + "VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'OPEN', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '" + now + "', '" + now + "', '2021-12-08', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, enrollment_end_date, start_date, end_date, owner_id) "
                + "VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'OPEN', 'PREPARE', '디우의 뤼액트 스터디입니다.', 4, 5, '" + now + "', '" + now + "', '2021-11-09', '2021-11-10', '2021-12-08', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, owner_id) "
                + "VALUES (3, 'javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'OPEN', 'PREPARE', '그린론의 자바스크립트 접해보기', 3, 20, '" + now + "', '" + now + "', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, max_member_count, created_date, last_modified_date, owner_id) "
                + "VALUES (4, 'HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'CLOSE', 'PREPARE', '디우의 HTTP 정복하기', 5, '" + now + "', '" + now + "', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, created_date, last_modified_date, owner_id, start_date) "
                + "VALUES (5, '알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'CLOSE', 'PREPARE', '알고리즘을 TDD로 풀자의 베루스입니다.', 1, '" + now + "', '" + now + "', 4, '2021-12-06')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruit_status, study_status, description, current_member_count, created_date, last_modified_date, owner_id, start_date, enrollment_end_date, end_date) "
                + "VALUES (6, 'Linux 스터디', '리눅스 설명', 'linux thumbnail', 'CLOSE', 'PREPARE', 'Linux를 공부하자의 베루스입니다.', 1, '" + now + "', '" + now + "', 4, '2021-12-06', '2021-12-07', '2022-01-07')");

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

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 3)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 4)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 1)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 2)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (2, 4)");

        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (3, 3)");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (3, 4)");
    }


    @BeforeEach
    void setUp() {
        sut = new SearchingStudyController(
                new SearchingStudyService(studySummaryDao, studyDetailsDao, memberDao, tagDao));
    }

    @DisplayName("페이징 정보로 스터디 목록 조회")
    @Test
    public void getStudies() {
        ResponseEntity<StudiesResponse> response = sut.getStudies(PageRequest.of(0, 3));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isTrue();
        assertThat(response.getBody().getStudies())
                .hasSize(3)
                .extracting("id", "title", "excerpt", "thumbnail", "recruitStatus")
                .containsExactlyElementsOf(List.of(
                        tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple(2L, "React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple(3L, "javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"))
                );
    }

    @DisplayName("빈 문자열로 검색시 전체 스터디 목록에서 조회")
    @Test
    void searchByBlankKeyword() {
        ResponseEntity<StudiesResponse> response = sut
                .searchStudies("", emptyList(), emptyList(), emptyList(),
                        PageRequest.of(0, 3)
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isTrue();
        assertThat(response.getBody().getStudies())
                .hasSize(3)
                .extracting("id", "title", "excerpt", "thumbnail", "recruitStatus")
                .containsExactlyElementsOf(List.of(
                        tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple(2L, "React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple(3L, "javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"))
                );
    }

    @DisplayName("문자열로 검색시 해당되는 스터디 목록에서 조회")
    @Test
    void searchByKeyword() {
        ResponseEntity<StudiesResponse> response = sut
                .searchStudies("Java 스터디", emptyList(), emptyList(), emptyList(),
                        PageRequest.of(0, 3)
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isFalse();
        assertThat(response.getBody().getStudies())
                .hasSize(1)
                .extracting("id", "title", "excerpt", "thumbnail", "recruitStatus")
                .contains(tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
    }

    @DisplayName("앞뒤 공백을 제거한 문자열로 스터디 목록 조회")
    @Test
    void searchWithTrimKeyword() {
        ResponseEntity<StudiesResponse> response = sut
                .searchStudies("   Java 스터디   ", emptyList(), emptyList(), emptyList(), PageRequest.of(0, 3));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isFalse();
        assertThat(response.getBody().getStudies())
                .hasSize(1)
                .extracting("id", "title", "excerpt", "thumbnail", "recruitStatus")
                .contains(tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
    }

    @DisplayName("다른 종류의 필터들은 AND 조건으로 스터디 목록을 조회")
    @Test
    void searchByDifferentKindFilters() {
        List<Long> tags = List.of(5L); // React
        List<Long> areas = List.of(3L); // BE

        ResponseEntity<StudiesResponse> response = sut
                .searchStudies("", emptyList(), areas, tags, PageRequest.of(0, 3));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isFalse();
        assertThat(response.getBody().getStudies()).hasSize(0);
    }

    @DisplayName("같은 종류의 필터들은 OR 조건으로 스터디 목록을 조회")
    @Test
    void searchBySameAndDifferentKindFilters() {
        List<Long> generationIds = List.of(2L); // 4기
        List<Long> areaIds = List.of(3L, 4L); // BE, FE
        List<Long> tagIds = List.of(1L, 5L); // Java, React
        ResponseEntity<StudiesResponse> response = sut
                .searchStudies("", generationIds, areaIds, tagIds, PageRequest.of(0, 3));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isFalse();
        assertThat(response.getBody().getStudies())
                .hasSize(2)
                .extracting("id", "title", "excerpt", "thumbnail", "recruitStatus")
                .contains(
                        tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple(2L, "React 스터디", "리액트 설명", "react thumbnail", "OPEN")
                );
    }

    @DisplayName("스터디 상세 정보를 조회할 수 있다.")
    @Test
    public void getStudyDetails() {
        StudyDetailsData expect = StudyDetailsData.builder()
                // Study Content
                .id(1L).title("Java 스터디").excerpt("자바 설명").thumbnail("java thumbnail")
                .status("OPEN").description("그린론의 우당탕탕 자바 스터디입니다.").createdDate(LocalDate.now())
                // Study Participant
                .currentMemberCount(3).maxMemberCount(10)
                .owner(new MemberData(2L, "greenlawn", "https://image", "github.com"))
                // Study Period
                .startDate(LocalDate.of(2021, 12, 8))
                .build();

        final List<Tuple> expectParticipants = List.of(
                tuple(3L, "dwoo", "https://image", "github.com"),
                tuple(4L, "verus", "https://image", "github.com")
        );

        final List<Tuple> expectAttachedTags = List.of(
                tuple("Java", "자바"),
                tuple("4기", "우테코4기"),
                tuple("BE", "백엔드")
        );

        final ResponseEntity<StudyDetailResponse> response = sut.getStudyDetails(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertStudyContent(response.getBody(), expect);
        assertStudyParticipants(response.getBody(), expect, expectParticipants);
        assertStudyPeriod(response.getBody(), expect);
        assertAttachedTags(response.getBody().getTags(), expectAttachedTags);
    }

    @DisplayName("선택적으로 입력 가능한 정보를 포함한 스터디 상세 정보를 조회할 수 있다.")
    @Test
    public void getStudyDetailsWithOptional() {
        final StudyDetailsData expect = StudyDetailsData.builder()
                // Study Content
                .id(2L).title("React 스터디").excerpt("리액트 설명").thumbnail("react thumbnail")
                .status("OPEN").description("디우의 뤼액트 스터디입니다.").createdDate(LocalDate.now())
                // Study Participant
                .currentMemberCount(4).maxMemberCount(5)
                .owner(new MemberData(3L, "dwoo", "https://image", "github.com"))
                // Study Period
                .enrollmentEndDate(LocalDate.of(2021, 11, 9))
                .startDate(LocalDate.of(2021, 11, 10))
                .endDate(LocalDate.of(2021, 12, 8))
                .build();

        final List<Tuple> expectParticipants = List.of(
                tuple(1L, "jjanggu", "https://image", "github.com"),
                tuple(2L, "greenlawn", "https://image", "github.com"),
                tuple(4L, "verus", "https://image", "github.com")
        );

        final List<Tuple> expectAttachedTags = List.of(
                tuple("4기", "우테코4기"),
                tuple("FE", "프론트엔드"),
                tuple("React", "리액트")
        );

        final ResponseEntity<StudyDetailResponse> response = sut.getStudyDetails(2L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertStudyContent(response.getBody(), expect);
        assertStudyParticipants(response.getBody(), expect, expectParticipants);
        assertStudyPeriod(response.getBody(), expect);
        assertAttachedTags(response.getBody().getTags(), expectAttachedTags);
    }

    @DisplayName("스터디 참여자와 부착된 태그가 없는 스터디의 세부사항 조회")
    @Test
    void getNotHasParticipantsAndAttachedTagsStudyDetails() {

        final StudyDetailsData expect = StudyDetailsData.builder()
                // Study Content
                .id(6L).title("Linux 스터디").excerpt("리눅스 설명").thumbnail("linux thumbnail")
                .status("CLOSE").description("Linux를 공부하자의 베루스입니다.").createdDate(LocalDate.now())
                // Study Participant
                .currentMemberCount(1)
                .owner(new MemberData(4L, "verus", "https://image", "github.com"))
                // Study Period
                .startDate(LocalDate.of(2021, 12, 6))
                .enrollmentEndDate(LocalDate.of(2021, 12, 7))
                .endDate(LocalDate.of(2022, 1, 7))
                .build();

        final List<Tuple> expectParticipants = List.of();
        final List<Tuple> expectAttachedTags = List.of();

        final ResponseEntity<StudyDetailResponse> response = sut.getStudyDetails(6L);
        final StudyDetailResponse actual = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual).isNotNull();
        assertStudyContent(actual, expect);
        assertStudyParticipants(actual, expect, expectParticipants);
        assertStudyPeriod(actual, expect);
        assertAttachedTags(actual.getTags(), expectAttachedTags);
    }

    private void assertStudyContent(final StudyDetailResponse actual, final StudyDetailsData expect) {
        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getTitle()).isEqualTo(expect.getTitle());
        assertThat(actual.getExcerpt()).isEqualTo(expect.getExcerpt());
        assertThat(actual.getThumbnail()).isEqualTo(expect.getThumbnail());
        assertThat(actual.getRecruitStatus()).isEqualTo(expect.getRecruitStatus());
        assertThat(actual.getDescription()).isEqualTo(expect.getDescription());
        assertThat(actual.getCreatedDate()).isEqualTo(expect.getCreatedDate().toString());
    }

    private void assertStudyParticipants(
            final StudyDetailResponse actual, final StudyDetailsData expect, List<Tuple> expectParticipants
    ) {
        final String expectedMaxMemberCount = NULL_TO_EMPTY_STRING_CONVERTER.apply(expect.getMaxMemberCount());

        assertThat(actual.getCurrentMemberCount()).isEqualTo(expect.getCurrentMemberCount());
        assertThat(actual.getMaxMemberCount()).isEqualTo(expectedMaxMemberCount);
        assertThat(actual.getOwner()).isEqualTo(expect.getOwner());
        assertThat(actual.getMembers())
                .hasSize(expectParticipants.size())
                .extracting("githubId", "username", "imageUrl", "profileUrl")
                .containsExactlyInAnyOrderElementsOf(expectParticipants);
    }

    private void assertStudyPeriod(final StudyDetailResponse actual, final StudyDetailsData expect) {
        final String expectedEnrollmentEndDate = NULL_TO_EMPTY_STRING_CONVERTER.apply(expect.getEnrollmentEndDate());
        final String expectedEndDate = NULL_TO_EMPTY_STRING_CONVERTER.apply(expect.getEndDate());

        assertThat(actual.getEnrollmentEndDate()).isEqualTo(expectedEnrollmentEndDate);
        assertThat(actual.getEndDate()).isEqualTo(expectedEndDate);
        assertThat(actual.getStartDate()).isEqualTo(expect.getStartDate().toString());
    }

    private void assertAttachedTags(List<TagData> actual, List<Tuple> expect) {
        assertThat(actual)
                .hasSize(expect.size())
                .filteredOn(tag -> tag.getId() != null)
                .extracting("name", "description")
                .containsExactlyInAnyOrderElementsOf(expect);
    }
}
