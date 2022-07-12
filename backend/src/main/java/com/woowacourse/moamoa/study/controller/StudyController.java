package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies")
public class StudyController {

    private final StudyService studyService;

    public StudyController(final StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping
    public ResponseEntity<StudiesResponse> getStudies(
            @PageableDefault(size = 5) final Pageable pageable
    ) {
        final StudiesResponse studiesResponse = studyService.getStudies(pageable);
        return ResponseEntity.ok().body(studiesResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<StudiesResponse> searchStudies(
            @RequestParam(required = false, defaultValue = "") final String title,
            @PageableDefault(size = 5) final Pageable pageable
    ) {
        final StudiesResponse studiesResponse = studyService.searchBy(title, pageable);
        return ResponseEntity.ok().body(studiesResponse);
    }
}
