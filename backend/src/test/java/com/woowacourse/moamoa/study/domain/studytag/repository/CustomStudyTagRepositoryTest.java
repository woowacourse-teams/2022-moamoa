package com.woowacourse.moamoa.study.domain.studytag.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.study.domain.Details;
import com.woowacourse.moamoa.tag.domain.Tag;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.studytag.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.studytag.StudySlice;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@RepositoryTest
class CustomStudyTagRepositoryTest {

    @Autowired
    private StudyTagRepository studyTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @DisplayName("필터만을 사용해 검색 목록을 필터링할 수 있다.")
    @Test
    public void searchByFilter() {
        // given
        final List<Tag> tags = tagRepository.findAllByNameContainingIgnoreCase("BE");

        final StudySearchCondition searchCondition = new StudySearchCondition("", tags);
        final PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        final StudySlice studySlice = studyTagRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(studies).hasSize(3)
                .filteredOn(study -> study.getId() != null)
                .extracting("details")
                .contains(
                        new Details("Java 스터디", "자바 설명", "java thumbnail", "OPEN", "그린론의 우당탕탕 자바 스터디입니다."),
                        new Details("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE", "디우의 HTTP 정복하기"),
                        new Details("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE", "알고리즘을 TDD로 풀자의 베루스입니다.")
                );
        assertThat(hasNext).isFalse();
    }

    @DisplayName("같은 카테고리의 경우에는 OR 조건으로 필터링한다.")
    @Test
    public void searchByFilterSameCategory() {
        // given
        final List<Tag> tags = tagRepository.findAllByNameContainingIgnoreCase("BE");
        tags.addAll(tagRepository.findAllByNameContainingIgnoreCase("FE"));

        final PageRequest pageRequest = PageRequest.of(0, 5);
        final StudySearchCondition searchCondition = new StudySearchCondition("", tags);

        // when
        final StudySlice studySlice = studyTagRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(hasNext).isFalse();
        assertThat(studies).hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("details.title", "details.excerpt", "details.thumbnail", "details.status")
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
        final List<Tag> tags = tagRepository.findAllByNameContainingIgnoreCase("BE");
        tags.addAll(tagRepository.findAllByNameContainingIgnoreCase("java"));

        final PageRequest pageRequest = PageRequest.of(0, 5);
        final StudySearchCondition searchCondition = new StudySearchCondition("", tags);

        // when
        final StudySlice studySlice = studyTagRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(studies).hasSize(1)
                .filteredOn(study -> study.getId() != null)
                .extracting("details.title", "details.excerpt", "details.thumbnail", "details.status")
                .contains(tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
        assertThat(hasNext).isFalse();
    }

    @DisplayName("제목과 필터를 함께 사용하여 필터링 할 수 있다.")
    @Test
    public void searchByTitleAndFilter() {
        // given
        final List<Tag> tags = tagRepository.findAllByNameContainingIgnoreCase("BE");

        final PageRequest pageRequest = PageRequest.of(0, 5);
        final StudySearchCondition searchCondition = new StudySearchCondition("java", tags);

        // when
        final StudySlice studySlice = studyTagRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(studies).hasSize(1)
                .filteredOn(study -> study.getId() != null)
                .extracting("details.title", "details.excerpt", "details.thumbnail", "details.status")
                .contains(tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
        assertThat(hasNext).isFalse();
    }

    @DisplayName("같은 종류의 필터는 OR, 다른 종류의 필터는 AND 조건으로 목록을 조회한다.")
    @Test
    public void searchByFilters() {
        // given
        Tag tag_4기 = tagRepository.findById(2L).orElseThrow();
        Tag tag_BE = tagRepository.findById(3L).orElseThrow();
        Tag tag_FE = tagRepository.findById(4L).orElseThrow();

        final PageRequest pageRequest = PageRequest.of(0, 5);
        final StudySearchCondition searchCondition = new StudySearchCondition("",
                List.of(tag_4기, tag_BE, tag_FE));

        // when
        final StudySlice studySlice = studyTagRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(studies).hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("details.title", "details.excerpt", "details.thumbnail", "details.status")
                .contains(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                );
        assertThat(hasNext).isFalse();
    }

    @DisplayName("같은 종류의 필터는 OR, 다른 종류의 필터는 AND 조건으로 목록을 조회한다.")
    @Test
    public void searchByFiltersWithTitle() {
        // given
        Tag tag_4기 = tagRepository.findById(2L).orElseThrow();
        Tag tag_BE = tagRepository.findById(3L).orElseThrow();
        Tag tag_FE = tagRepository.findById(4L).orElseThrow();

        final PageRequest pageRequest = PageRequest.of(0, 5);
        final StudySearchCondition searchCondition = new StudySearchCondition("java",
                List.of(tag_4기, tag_BE, tag_FE));

        // when
        final StudySlice studySlice = studyTagRepository.searchBy(searchCondition, pageRequest);

        // then
        final List<Study> studies = studySlice.getStudies();
        final boolean hasNext = studySlice.isHasNext();

        assertThat(studies).hasSize(2)
                .filteredOn(study -> study.getId() != null)
                .extracting("details.title", "details.excerpt", "details.thumbnail", "details.status")
                .contains(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN")
                );
        assertThat(hasNext).isFalse();
    }
}
