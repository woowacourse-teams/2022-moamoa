package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies")
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public ResponseEntity<Void> createStudy(
            @AuthenticationPrincipal final Long githubId,
            @Valid @RequestBody(required = false) final CreatingStudyRequest creatingStudyRequest
    ) {
        final Study study = studyService.createStudy(githubId, creatingStudyRequest);
        return ResponseEntity.created(URI.create("/api/studies/" + study.getId())).build();
    }

    @PostMapping("/{study-id}")
    public ResponseEntity<Void> participateStudy(@AuthenticationPrincipal final Long githubId,
                                                 @PathVariable("study-id") final Long studyId
    ) {
        studyService.participateStudy(githubId, studyId);
        return ResponseEntity.ok().build();
    }
}
