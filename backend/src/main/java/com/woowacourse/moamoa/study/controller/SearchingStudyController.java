package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.study.query.SearchingTags;
import com.woowacourse.moamoa.study.service.SearchingStudyService;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class SearchingStudyController {

    private final SearchingStudyService searchingStudyService;

    @GetMapping
    public ResponseEntity<StudiesResponse> getStudies(
            @RequestParam(required = false) final Long id,
            @RequestParam(required = false) final LocalDateTime createdAt,
            @PageableDefault(size = 5) final Pageable pageable
    ) {
        final StudiesResponse studiesResponse = searchingStudyService.getStudies("", SearchingTags.emptyTags(), id, createdAt, pageable);
        return ResponseEntity.ok().body(studiesResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<StudiesResponse> searchStudies(
            @RequestParam(required = false) final Long id,
            @RequestParam(required = false) final LocalDateTime createdAt,
            @RequestParam(required = false, defaultValue = "") final String title,
            @RequestParam(required = false, name = "generation", defaultValue = "") final List<Long> generations,
            @RequestParam(required = false, name = "area", defaultValue = "") final List<Long> areas,
            @RequestParam(required = false, name = "subject", defaultValue = "") final List<Long> tags,
            @PageableDefault(size = 5) final Pageable pageable
    ) {
        final SearchingTags searchingTags = new SearchingTags(generations, areas, tags);
        final StudiesResponse studiesResponse = searchingStudyService.getStudies(title.trim(), searchingTags, id, createdAt, pageable);
        return ResponseEntity.ok().body(studiesResponse);
    }

    @GetMapping("/{study-id}")
    public ResponseEntity<StudyDetailResponse> getStudyDetails(@PathVariable(name = "study-id") Long studyId) {
        final StudyDetailResponse response = searchingStudyService.getStudyDetails(studyId);
        return ResponseEntity.ok().body(response);
    }
}
