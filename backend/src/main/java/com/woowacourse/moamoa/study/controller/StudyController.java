package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.controller.request.TagRequest;
import com.woowacourse.moamoa.study.service.StudyTagService;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final StudyTagService studyTagService;

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
            @ModelAttribute TagRequest tagRequest,
            @PageableDefault(size = 5) final Pageable pageable
    ) {
        final StudiesResponse studiesResponse = studyTagService.searchBy(title, tagRequest, pageable);
        return ResponseEntity.ok().body(studiesResponse);
    }

    @GetMapping("/{study-id}")
    public ResponseEntity<StudyDetailResponse> getStudyDetails(@PathVariable(name = "study-id") Long studyId) {
        final StudyDetailResponse response = studyService.getStudyDetails(studyId);
        return ResponseEntity.ok().body(response);
    }
}
