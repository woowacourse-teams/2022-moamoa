package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudyController {

    private final StudyService studyService;

    public StudyController(final StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping("/api/studies")
    public ResponseEntity<StudiesResponse> getStudies(
            @PageableDefault(size = 5) final Pageable pageable
    ) {
        final StudiesResponse studiesResponse = studyService.getStudies(pageable);
        return ResponseEntity.ok().body(studiesResponse);
    }
}
