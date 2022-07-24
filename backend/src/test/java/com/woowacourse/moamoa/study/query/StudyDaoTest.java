package com.woowacourse.moamoa.study.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.study.domain.query.StudyDao;
import com.woowacourse.moamoa.study.domain.query.StudySearchCondition;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@RepositoryTest
public class StudyDaoTest {

    private static final long BE_ID = 3L;
    private static final long FE_ID = 4L;
    private static final long JAVA_ID = 1L;
    private static final long FOUR_GENERATION_ID = 2L;

    @Autowired
    private StudyDao studyDao;

    @DisplayName("필터만을 사용해 검색 목록을 필터링할 수 있다.")
    @Test
    public void searchByFilter() {
        // given
        final StudySearchCondition searchCondition = new StudySearchCondition(List.of(), List.of(BE_ID), List.of(), "");
        final PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        final StudiesResponse studiesResponse = studyDao.searchBy(searchCondition, pageRequest);
        final List<StudyResponse> studies = studiesResponse.getStudies();

        // then
        assertThat(studiesResponse.isHasNext()).isFalse();
        assertThat(studies)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .contains(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                );
    }

    @DisplayName("같은 카테고리의 경우에는 OR 조건으로 필터링한다.")
    @Test
    public void searchByFilterSameCategory() {
        // given
        final StudySearchCondition searchCondition = new StudySearchCondition(List.of(), List.of(BE_ID, FE_ID),
                List.of(), "");
        final PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        final StudiesResponse studiesResponse = studyDao.searchBy(searchCondition, pageRequest);
        final List<StudyResponse> studies = studiesResponse.getStudies();

        // then
        assertThat(studiesResponse.isHasNext()).isFalse();
        assertThat(studies).hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .contains(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE"));
    }

    @DisplayName("서로 다른 카테고리의 필터를 이용하여 필터링할 수 있다.")
    @Test
    public void searchByFilterWithAnotherCategory() {
        // given
        final StudySearchCondition searchCondition = new StudySearchCondition(List.of(),
                List.of(BE_ID, FE_ID), List.of(JAVA_ID), "");
        final PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        final StudiesResponse studiesResponse = studyDao.searchBy(searchCondition, pageRequest);
        final List<StudyResponse> studies = studiesResponse.getStudies();

        // then
        assertThat(studiesResponse.isHasNext()).isFalse();
        assertThat(studies).hasSize(1)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .contains(tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
    }

    @DisplayName("제목과 필터를 함께 사용하여 필터링 할 수 있다.")
    @Test
    public void searchByTitleAndFilter() {
        // given
        final StudySearchCondition searchCondition = new StudySearchCondition(List.of(),
                List.of(BE_ID), List.of(), "java");
        final PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        final StudiesResponse studiesResponse = studyDao.searchBy(searchCondition, pageRequest);
        final List<StudyResponse> studies = studiesResponse.getStudies();

        // then
        assertThat(studiesResponse.isHasNext()).isFalse();
        assertThat(studies).hasSize(1)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .contains(tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
    }

    @DisplayName("같은 종류의 필터는 OR, 다른 종류의 필터는 AND 조건으로 목록을 조회한다.")
    @Test
    public void searchByFilters() {
        // given
        final StudySearchCondition searchCondition = new StudySearchCondition(List.of(FOUR_GENERATION_ID),
                List.of(BE_ID, FE_ID), List.of(), "");
        final PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        final StudiesResponse studiesResponse = studyDao.searchBy(searchCondition, pageRequest);
        final List<StudyResponse> studies = studiesResponse.getStudies();

        // then
        assertThat(studiesResponse.isHasNext()).isFalse();
        assertThat(studies).hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .contains(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                );
    }

    @DisplayName("같은 종류의 필터는 OR, 다른 종류의 필터는 AND 조건으로 목록을 조회한다.")
    @Test
    public void searchByFiltersWithTitle() {
        // given
        final StudySearchCondition searchCondition = new StudySearchCondition(List.of(FOUR_GENERATION_ID),
                List.of(BE_ID, FE_ID), List.of(), "java");
        final PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        final StudiesResponse studiesResponse = studyDao.searchBy(searchCondition, pageRequest);
        final List<StudyResponse> studies = studiesResponse.getStudies();

        // then
        assertThat(studiesResponse.isHasNext()).isFalse();
        assertThat(studies).hasSize(2)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .contains(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN")
                );
    }
}
