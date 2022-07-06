package com.woowacourse.moamoa.study.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StudyControllerTest {

    private StudyRepository studyRepository;

    private StudyController studyController;

    @BeforeEach
    void setUp() {
        studyRepository = Mockito.mock(StudyRepository.class);

        when(studyRepository.findAll(PageRequest.of(0, 3)))
                .thenReturn(
                        new SliceImpl<>(List.of(
                                new Study(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                new Study(2L, "React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                                new Study(3L, "javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN")
                        ), Pageable.unpaged(), true)
                );

        studyController = new StudyController(new StudyService(studyRepository));
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
                .extracting("id", "title", "description", "thumbnail", "status")
                .containsExactlyElementsOf(List.of(
                        tuple(1L, "Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple(2L, "React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple(3L, "javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"))
                );

        verify(studyRepository).findAll(PageRequest.of(0, 3));
    }
}
