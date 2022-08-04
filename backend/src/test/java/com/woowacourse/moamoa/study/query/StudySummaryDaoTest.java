package com.woowacourse.moamoa.study.query;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static com.woowacourse.moamoa.study.domain.StudyStatus.DONE;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.AttachedTag;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Content;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.RecruitPlanner;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.StudyPlanner;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import com.woowacourse.moamoa.tag.query.TagDao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
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
    private MemberRepository memberRepository;

    @Autowired
    private StudySummaryDao studySummaryDao;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private EntityManager em;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Member jjanggu;
    private Member greenlawn;
    private Member dwoo;
    private Member verus;

    @BeforeEach
    void initDataBase() {
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (1, 'generation')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (2, 'area')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (3, 'subject')");

        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (1, 'Java', '자바', 3)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (2, '4기', '우테코4기', 1)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (3, 'BE', '백엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (4, 'FE', '프론트엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (5, 'React', '리액트', 3)");

        jjanggu = memberRepository.save(new Member(1L, "jjanggu", "https://image", "github.com"));
        greenlawn = memberRepository.save(new Member(2L, "greenlawn", "https://image", "github.com"));
        dwoo = memberRepository.save(new Member(3L, "dwoo", "https://image", "github.com"));
        verus = memberRepository.save(new Member(4L, "verus", "https://image", "github.com"));

        studyRepository.save(
                new Study(
                        new Content("Java 스터디", "자바 설명", "java thumbnail", "그린론의 우당탕탕 자바 스터디입니다."),
                        new Participants(greenlawn.getId(), Set.of(dwoo.getId(), verus.getId())),
                        new RecruitPlanner(10, RECRUITMENT_START, LocalDate.of(2022, 12, 9)),
                        new StudyPlanner(LocalDate.of(2022, 12, 9), LocalDate.of(2022, 12, 11), PREPARE),
                        new AttachedTags(List.of(new AttachedTag(1L), new AttachedTag(2L), new AttachedTag(3L))),
                        LocalDateTime.of(2022, 12, 8, 0, 0, 0))
        );

        studyRepository.save(
                new Study(
                        new Content("React 스터디", "리액트 설명", "react thumbnail", "디우의 뤼액트 스터디입니다."),
                        new Participants(dwoo.getId(), Set.of(jjanggu.getId(), greenlawn.getId(), verus.getId())),
                        new RecruitPlanner(5, RECRUITMENT_START, LocalDate.of(2022, 12, 9)),
                        new StudyPlanner(LocalDate.of(2022, 12, 9), LocalDate.of(2022, 12, 10), PREPARE),
                        new AttachedTags(List.of(new AttachedTag(2L), new AttachedTag(4L), new AttachedTag(5L))),
                        LocalDateTime.of(2022, 12, 8, 1, 0, 0))
        );

        studyRepository.save(
                new Study(
                        new Content("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "그린론의 자바스크립트 접해보기"),
                        new Participants(dwoo.getId(), Set.of(verus.getId())),
                        new RecruitPlanner(20, RECRUITMENT_START, LocalDate.of(2022, 12, 9)),
                        new StudyPlanner(LocalDate.of(2022, 12, 9), LocalDate.of(2022, 12, 11), PREPARE),
                        new AttachedTags(List.of(new AttachedTag(2L), new AttachedTag(4L))),
                        LocalDateTime.of(2022, 12, 8, 2, 0, 0))
        );

        studyRepository.save(
                new Study(
                        new Content("HTTP 스터디", "HTTP 설명", "http thumbnail", "디우의 HTTP 정복하기"),
                        new Participants(jjanggu.getId(), Set.of(dwoo.getId(), verus.getId())),
                        new RecruitPlanner(5, RECRUITMENT_END, LocalDate.of(2023, 11, 8)),
                        new StudyPlanner(LocalDate.of(2023, 12, 9), LocalDate.of(2023, 12, 11), DONE),
                        new AttachedTags(List.of(new AttachedTag(2L), new AttachedTag(3L))),
                        LocalDateTime.of(2023, 11, 7, 0, 0, 0))
        );

        studyRepository.save(
                new Study(
                        new Content("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "알고리즘을 TDD로 풀자의 베루스입니다."),
                        new Participants(dwoo.getId(), Set.of(verus.getId())),
                        new RecruitPlanner(10, RECRUITMENT_END, LocalDate.of(2023, 11, 8)),
                        new StudyPlanner(LocalDate.of(2023, 12, 9), LocalDate.of(2023, 12, 11), DONE),
                        new AttachedTags(List.of()),
                        LocalDateTime.of(2023, 11, 7, 1, 0, 0))
        );

        studyRepository.save(
                new Study(
                        new Content("Linux 스터디", "리눅스 설명", "linux thumbnail", "Linux를 공부하자의 베루스입니다."),
                        new Participants(dwoo.getId(), Set.of(verus.getId())),
                        new RecruitPlanner(10, RECRUITMENT_END, LocalDate.of(2023, 11, 8)),
                        new StudyPlanner(LocalDate.of(2023, 12, 9), LocalDate.of(2023, 12, 11), DONE),
                        new AttachedTags(List.of()),
                        LocalDateTime.of(2023, 11, 7, 2, 0, 0))
        );

        em.flush();
        em.clear();
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
                tuple("Linux 스터디", "리눅스 설명", "linux thumbnail", "RECRUITMENT_END"),
                tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "RECRUITMENT_END"),
                tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "RECRUITMENT_END"),
                tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "RECRUITMENT_START"),
                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START"),
                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START")
        );

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
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "RECRUITMENT_START"),
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START")
                );
    }

    @DisplayName("빈 키워드와 함께 페이징 정보를 사용해 스터디 목록 조회")
    @Test
    public void findByBlankTitle() {
        final Slice<StudySummaryData> response = studySummaryDao.searchBy("", SearchingTags.emptyTags(),
                PageRequest.of(0, 5));

        assertThat(response.hasNext()).isTrue();
        assertThat(response.getContent())
                .hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "recruitmentStatus")
                .containsExactly(
                        tuple("Linux 스터디", "리눅스 설명", "linux thumbnail", "RECRUITMENT_END"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "RECRUITMENT_END"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "RECRUITMENT_END"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "RECRUITMENT_START"),
                        tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START")
                );
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
                                tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "RECRUITMENT_END"),
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START")
                        )),
                Arguments.of(new SearchingTags(List.of(6L), emptyList(), emptyList()), List.of()), // 3기,
                Arguments.of(new SearchingTags(emptyList(), emptyList(), List.of(1L, 5L)), // Java, React
                        List.of(
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START"),
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START")
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
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START"),
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START")
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
                                tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "RECRUITMENT_END"),
                                tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "RECRUITMENT_START"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START")
                        ),
                        true
                ),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L, 4L), List.of(1L, 5L)),
                        // 4기, FE, BE, Java, React
                        List.of(
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "RECRUITMENT_START"),
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "RECRUITMENT_START")
                        ),
                        false
                )
        );
    }
}
