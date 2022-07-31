package com.woowacourse.moamoa.study.query;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
public class StudySummaryDaoTest {

    @Autowired
    private StudySummaryDao studySummaryDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDataBase() {
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (1, 1, 'jjanggu', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (2, 2, 'greenlawn', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (3, 3, 'dwoo', 'https://image', 'github.com')");
        jdbcTemplate.update("INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (4, 4, 'verus', 'https://image', 'github.com')");

        final LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, start_date, owner_id) "
                + "VALUES (1, 'Java 스터디', '자바 설명', 'java thumbnail', 'RECRUITMENT_START', 'PREPARE', '그린론의 우당탕탕 자바 스터디입니다.', 3, 10, '" + now + "', '" + now + "', '2021-12-08', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, enrollment_end_date, start_date, end_date, owner_id) "
                + "VALUES (2, 'React 스터디', '리액트 설명', 'react thumbnail', 'RECRUITMENT_START', 'PREPARE', '디우의 뤼액트 스터디입니다.', 4, 5, '" + now + "', '" + now + "', '2021-11-09', '2021-11-10', '2021-12-08', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, max_member_count, created_date, last_modified_date, owner_id) "
                + "VALUES (3, 'javaScript 스터디', '자바스크립트 설명', 'javascript thumbnail', 'RECRUITMENT_START', 'PREPARE', '그린론의 자바스크립트 접해보기', 3, 20, '" + now + "', '" + now + "', 2)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, max_member_count, created_date, last_modified_date, owner_id) "
                + "VALUES (4, 'HTTP 스터디', 'HTTP 설명', 'http thumbnail', 'RECRUITMENT_END', 'PREPARE', '디우의 HTTP 정복하기', 5, '" + now + "', '" + now + "', 3)");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, created_date, last_modified_date, owner_id, start_date) "
                + "VALUES (5, '알고리즘 스터디', '알고리즘 설명', 'algorithm thumbnail', 'RECRUITMENT_END', 'PREPARE', '알고리즘을 TDD로 풀자의 베루스입니다.', 1, '" + now + "', '" + now + "', 4, '2021-12-06')");
        jdbcTemplate.update("INSERT INTO study(id, title, excerpt, thumbnail, recruitment_status, study_status, description, current_member_count, created_date, last_modified_date, owner_id, start_date, enrollment_end_date, end_date) "
                + "VALUES (6, 'Linux 스터디', '리눅스 설명', 'linux thumbnail', 'RECRUITMENT_END', 'PREPARE', 'Linux를 공부하자의 베루스입니다.', 1, '" + now + "', '" + now + "', 4, '2021-12-06', '2021-12-07', '2022-01-07')");

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


    @DisplayName("페이징 정보를 사용해 스터디 목록 조회")
    @ParameterizedTest
    @MethodSource("providePageableAndExpect")
    public void findAllByPageable(Pageable pageable, List<Tuple> expectedTuples, boolean expectedHasNext) {
        final Slice<StudySummaryData> response = studySummaryDao.searchBy("", SearchingTags.emptyTags(), pageable);

        assertThat(response.hasNext()).isEqualTo(expectedHasNext);
        assertThat(response.getContent())
                .hasSize(expectedTuples.size())
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "recruitmentStatus")
                .containsExactlyElementsOf(expectedTuples);
    }

    private static Stream<Arguments> providePageableAndExpect() {
        List<Tuple> tuples = List.of(
                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START"),
                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START"),
                tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "RECRUITMENT_START"),
                tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "RECRUITMENT_END"),
                tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "RECRUITMENT_END"),
                tuple("Linux 스터디", "리눅스 설명", "linux thumbnail", "RECRUITMENT_END"));

        return Stream.of(
                Arguments.of(PageRequest.of(0, 3), tuples.subList(0, 3), true),
                Arguments.of(PageRequest.of(1, 2), tuples.subList(2, 4), true),
                Arguments.of(PageRequest.of(1, 3), tuples.subList(3, 6), false)
        );
    }

    @DisplayName("키워드와 함께 페이징 정보를 사용해 스터디 목록 조회")
    @Test
    public void findByTitleContaining() {
        final Slice<StudySummaryData> response = studySummaryDao
                .searchBy("java", SearchingTags.emptyTags(), PageRequest.of(0, 3));

        assertThat(response.hasNext()).isFalse();
        assertThat(response.getContent())
                .hasSize(2)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "recruitmentStatus")
                .containsExactly(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "RECRUITMENT_START"));
    }

    @DisplayName("빈 키워드와 함께 페이징 정보를 사용해 스터디 목록 조회")
    @Test
    public void findByBlankTitle() {
        final Slice<StudySummaryData> response = studySummaryDao.searchBy("", SearchingTags.emptyTags(), PageRequest.of(0, 5));

        assertThat(response.hasNext()).isTrue();
        assertThat(response.getContent())
                .hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "recruitmentStatus")
                .containsExactly(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START"),
                        tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "RECRUITMENT_START"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "RECRUITMENT_END"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "RECRUITMENT_END"));
    }

    @DisplayName("한 가지 종류의 필터로 스터디 목록을 조회")
    @ParameterizedTest
    @MethodSource("provideOneKindFiltersAndExpectResult")
    void searchByOneKindFilter(SearchingTags searchingTags, List<Tuple> tuples) {
        Slice<StudySummaryData> response = studySummaryDao.searchBy("", searchingTags, PageRequest.of(0, 3));

        assertThat(response.hasNext()).isFalse();
        assertThat(response.getContent())
                .hasSize(tuples.size())
                .extracting("title", "excerpt", "thumbnail", "recruitmentStatus")
                .containsExactlyElementsOf(tuples);
    }

    private static Stream<Arguments> provideOneKindFiltersAndExpectResult() {
        return Stream.of(
                Arguments.of(new SearchingTags(emptyList(), emptyList(), List.of(5L)), // React
                        List.of(tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START"))),
                Arguments.of(new SearchingTags(emptyList(), List.of(3L), emptyList()), // BE
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START"),
                                tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "RECRUITMENT_END")
                        )),
                Arguments.of(new SearchingTags(List.of(6L), emptyList(), emptyList()), List.of()), // 3기,
                Arguments.of(new SearchingTags(emptyList(), emptyList(), List.of(1L, 5L)), // Java, React
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START")
                        ))
        );
    }

    @DisplayName("다른 종류의 카테고리인 경우 OR 조건으로 조회")
    @ParameterizedTest
    @MethodSource("provideFiltersAndExpectResult")
    void searchByUnableToFoundTags(SearchingTags searchingTags, List<Tuple> tuples, boolean hasNext) {
        Slice<StudySummaryData> response = studySummaryDao.searchBy("", searchingTags, PageRequest.of(0, 3));

        assertThat(response.hasNext()).isEqualTo(hasNext);
        assertThat(response.getContent())
                .hasSize(tuples.size())
                .extracting("title", "excerpt", "thumbnail", "recruitmentStatus")
                .containsExactlyElementsOf(tuples);
    }

    private static Stream<Arguments> provideFiltersAndExpectResult() {
        return Stream.of(
                Arguments.of(new SearchingTags(List.of(2L), emptyList(), List.of(1L, 5L)), // 4기, Java, React
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START")
                        ),
                        false
                ),
                Arguments.of(new SearchingTags(emptyList(), List.of(3L), List.of(5L)), // BE, React
                        List.of(),
                        false),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L), List.of(1L)), // 4기, BE, Java
                        List.of(tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START")),
                        false
                ),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L, 4L), emptyList()), // 4기, FE, BE
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START"),
                                tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "RECRUITMENT_START")
                        ),
                        true
                ),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L, 4L), List.of(1L, 5L)), // 4기, FE, BE, Java, React
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START")
                        ),
                        false
                )
        );
    }
}
