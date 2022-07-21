package com.woowacourse.moamoa.study.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.study.repository.StudyRepository;
import com.woowacourse.moamoa.study.domain.studytag.repository.StudyTagRepository;
import com.woowacourse.moamoa.study.service.StudyDetailService;
import com.woowacourse.moamoa.study.service.StudyTagService;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
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

    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private StudyTagRepository studyTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MemberRepository memberRepository;

    private SearchingStudiesController studyController;

    @BeforeEach
    void setUp() {
        studyController = new SearchingStudiesController(new StudyDetailService(studyRepository, memberRepository, tagRepository),
                new StudyTagService(studyTagRepository, studyRepository, tagRepository));
    }

    @DisplayName("페이징 정보로 스터디 목록 조회")
    @Test
    public void getStudies() {
        ResponseEntity<StudiesResponse> response = studyController.getStudies(PageRequest.of(0, 3));

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
        ResponseEntity<StudiesResponse> response = studyController.searchStudies("", null, PageRequest.of(0, 3));

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
        ResponseEntity<StudiesResponse> response = studyController.searchStudies("Java 스터디", null,
                PageRequest.of(0, 3));

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
        ResponseEntity<StudiesResponse> response = studyController
                .searchStudies("   Java 스터디   ", null, PageRequest.of(0, 3));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isHasNext()).isFalse();
        assertThat(response.getBody().getStudies())
                .hasSize(1)
                .extracting("id", "title", "excerpt", "thumbnail", "status")
                .contains(tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"));
    }
}
