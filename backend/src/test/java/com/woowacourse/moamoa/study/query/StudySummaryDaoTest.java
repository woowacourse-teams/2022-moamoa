package com.woowacourse.moamoa.study.query;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@RepositoryTest
public class StudySummaryDaoTest {

    @Autowired
    private StudySummaryDao studySummaryDao;

    @DisplayName("페이징 정보를 사용해 스터디 목록 조회")
    @ParameterizedTest
    @MethodSource("providePageableAndExpect")
    public void findAllByPageable(Pageable pageable, List<Tuple> expectedTuples, boolean expectedHasNext) {
        final Slice<StudySummaryData> response = studySummaryDao.searchBy("", SearchingTags.emptyTags(), pageable);

        assertThat(response.hasNext()).isEqualTo(expectedHasNext);
        assertThat(response.getContent())
                .hasSize(expectedTuples.size())
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .containsExactlyElementsOf(expectedTuples);
    }

    private static Stream<Arguments> providePageableAndExpect() {
        List<Tuple> tuples = List.of(
                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE"));

        return Stream.of(
                Arguments.of(PageRequest.of(0, 3), tuples.subList(0, 3), true),
                Arguments.of(PageRequest.of(1, 2), tuples.subList(2, 4), true),
                Arguments.of(PageRequest.of(1, 3), tuples.subList(3, 5), false)
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
                .extracting("title", "excerpt", "thumbnail", "status")
                .containsExactly(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"));
    }

    @DisplayName("빈 키워드와 함께 페이징 정보를 사용해 스터디 목록 조회")
    @Test
    public void findByBlankTitle() {
        final Slice<StudySummaryData> response = studySummaryDao.searchBy("", SearchingTags.emptyTags(), PageRequest.of(0, 5));

        assertThat(response.hasNext()).isFalse();
        assertThat(response.getContent())
                .hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .containsExactly(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE"));
    }

    @DisplayName("한 가지 종류의 필터로 스터디 목록을 조회")
    @ParameterizedTest
    @MethodSource("provideOneKindFiltersAndExpectResult")
    void searchByOneKindFilter(SearchingTags searchingTags, List<Tuple> tuples) {
        Slice<StudySummaryData> response = studySummaryDao.searchBy("", searchingTags, PageRequest.of(0, 3));

        assertThat(response.hasNext()).isFalse();
        assertThat(response.getContent())
                .hasSize(tuples.size())
                .extracting("title", "excerpt", "thumbnail", "status")
                .containsExactlyElementsOf(tuples);
    }

    private static Stream<Arguments> provideOneKindFiltersAndExpectResult() {
        return Stream.of(
                Arguments.of(new SearchingTags(emptyList(), emptyList(), List.of(5L)), // React
                        List.of(tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"))),
                Arguments.of(new SearchingTags(emptyList(), List.of(3L), emptyList()), // BE
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                                tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                        )),
                Arguments.of(new SearchingTags(List.of(6L), emptyList(), emptyList()), List.of()), // 3기,
                Arguments.of(new SearchingTags(emptyList(), emptyList(), List.of(1L, 5L)), // Java, React
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN")
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
                .extracting("title", "excerpt", "thumbnail", "status")
                .containsExactlyElementsOf(tuples);
    }

    private static Stream<Arguments> provideFiltersAndExpectResult() {
        return Stream.of(
                Arguments.of(new SearchingTags(List.of(2L), emptyList(), List.of(1L, 5L)), // 4기, Java, React
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN")
                        ),
                        false
                ),
                Arguments.of(new SearchingTags(emptyList(), List.of(3L), List.of(5L)), // BE, React
                        List.of(),
                        false),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L), List.of(1L)), // 4기, BE, Java
                        List.of(tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN")),
                        false
                ),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L, 4L), emptyList()), // 4기, FE, BE
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                                tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN")
                        ),
                        true
                ),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L, 4L), List.of(1L, 5L)), // 4기, FE, BE, Java, React
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN")
                        ),
                        false
                )
        );
    }
}
