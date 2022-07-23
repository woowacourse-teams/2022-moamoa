package com.woowacourse.moamoa.study.controller;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.study.query.StudyDao;
import com.woowacourse.moamoa.study.query.StudySummaryDao;
import com.woowacourse.moamoa.study.service.SearchingStudyService;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.tag.query.TagDao;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
public class StudyControllerTest {

    private SearchingStudyController searchingStudyController;

    @Autowired
    private StudySummaryDao studySummaryDao;

    @Autowired
    private StudyDao studyDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        searchingStudyController = new SearchingStudyController(
                new SearchingStudyService(studySummaryDao, studyDao, memberDao, tagDao));
    }

    @DisplayName("페이징 정보로 스터디 목록 조회")
    @Test
    public void getStudies() {
        ResponseEntity<StudiesResponse> response = searchingStudyController.getStudies(PageRequest.of(0, 3));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isTrue();
        assertThat(response.getBody().getStudies())
                .hasSize(3)
                .extracting("id", "title", "excerpt", "thumbnail", "status")
                .containsExactlyElementsOf(List.of(
                        tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple(2L, "React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple(3L, "javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"))
                );
    }

    @DisplayName("빈 문자열로 검색시 전체 스터디 목록에서 조회")
    @Test
    void searchByBlankKeyword() {
        ResponseEntity<StudiesResponse> response = searchingStudyController
                .searchStudies("", emptyList(), emptyList(), emptyList(),
                        PageRequest.of(0, 3)
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isTrue();
        assertThat(response.getBody().getStudies())
                .hasSize(3)
                .extracting("id", "title", "excerpt", "thumbnail", "status")
                .containsExactlyElementsOf(List.of(
                        tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple(2L, "React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple(3L, "javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"))
                );
    }

    @DisplayName("문자열로 검색시 해당되는 스터디 목록에서 조회")
    @Test
    void searchByKeyword() {
        ResponseEntity<StudiesResponse> response = searchingStudyController
                .searchStudies("Java 스터디", emptyList(), emptyList(), emptyList(),
                        PageRequest.of(0, 3)
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isFalse();
        assertThat(response.getBody().getStudies())
                .hasSize(1)
                .extracting("id", "title", "excerpt", "thumbnail", "status")
                .contains(tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
    }

    @DisplayName("앞뒤 공백을 제거한 문자열로 스터디 목록 조회")
    @Test
    void searchWithTrimKeyword() {
        ResponseEntity<StudiesResponse> response = searchingStudyController
                .searchStudies("   Java 스터디   ", emptyList(), emptyList(), emptyList(), PageRequest.of(0, 3));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isFalse();
        assertThat(response.getBody().getStudies())
                .hasSize(1)
                .extracting("id", "title", "excerpt", "thumbnail", "status")
                .contains(tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
    }

    @DisplayName("다른 종류의 필터들은 AND 조건으로 스터디 목록을 조회")
    @Test
    void searchByDifferentKindFilters() {
        List<Long> tags = List.of(5L); // React
        List<Long> areas = List.of(3L); // BE

        ResponseEntity<StudiesResponse> response = searchingStudyController
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
        ResponseEntity<StudiesResponse> response = searchingStudyController
                .searchStudies("", generationIds, areaIds, tagIds, PageRequest.of(0, 3));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isFalse();
        assertThat(response.getBody().getStudies())
                .hasSize(2)
                .extracting("id", "title", "excerpt", "thumbnail", "status")
                .contains(
                        tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple(2L, "React 스터디", "리액트 설명", "react thumbnail", "OPEN")
                );
    }
}
