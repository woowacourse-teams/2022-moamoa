package com.woowacourse.moamoa.study.domain.studyfilter.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.filter.domain.Filter;
import com.woowacourse.moamoa.filter.domain.repository.FilterRepository;
import com.woowacourse.moamoa.study.domain.study.Study;
import com.woowacourse.moamoa.study.domain.studyfilter.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.studyfilter.StudySlice;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class CustomStudyFilterRepositoryTest {

    @Autowired
    private StudyFilterRepository studyFilterRepository;

    @Autowired
    private FilterRepository filterRepository;

    @DisplayName("필터만을 사용해 검색 목록을 필터링할 수 있다.")
    @Test
    public void searchByFilter() {
        // given
        final List<Filter> filters = filterRepository.findAllByNameContainingIgnoreCase("BE");

        final StudySearchCondition searchCondition = new StudySearchCondition("", filters);
        final PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        final StudySlice studySlice = studyFilterRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(studies).hasSize(3)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .contains(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                );
        assertThat(hasNext).isFalse();
    }

    @DisplayName("같은 카테고리의 경우에는 OR 조건으로 필터링한다.")
    @Test
    public void searchByFilterSameCategory() {
        // given
        final List<Filter> filters = filterRepository.findAllByNameContainingIgnoreCase("BE");
        filters.addAll(filterRepository.findAllByNameContainingIgnoreCase("FE"));

        final PageRequest pageRequest = PageRequest.of(0, 5);
        final StudySearchCondition searchCondition = new StudySearchCondition("", filters);

        // when
        final StudySlice studySlice = studyFilterRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(hasNext).isFalse();
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
        final List<Filter> filters = filterRepository.findAllByNameContainingIgnoreCase("BE");
        filters.addAll(filterRepository.findAllByNameContainingIgnoreCase("java"));

        final PageRequest pageRequest = PageRequest.of(0, 5);
        final StudySearchCondition searchCondition = new StudySearchCondition("", filters);

        // when
        final StudySlice studySlice = studyFilterRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(studies).hasSize(1)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .contains(tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
        assertThat(hasNext).isFalse();
    }

    @DisplayName("제목과 필터를 함께 사용하여 필터링 할 수 있다.")
    @Test
    public void searchByTitleAndFilter() {
        // given
        final List<Filter> filters = filterRepository.findAllByNameContainingIgnoreCase("BE");

        final PageRequest pageRequest = PageRequest.of(0, 5);
        final StudySearchCondition searchCondition = new StudySearchCondition("java", filters);

        // when
        final StudySlice studySlice = studyFilterRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(studies).hasSize(1)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "status")
                .contains(tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
        assertThat(hasNext).isFalse();
    }
}
