package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            @Valid @RequestBody(required = false) final StudyRequest studyRequest
    ) {
        final Study study = studyService.createStudy(githubId, studyRequest);
        return ResponseEntity.created(URI.create("/api/studies/" + study.getId())).build();
    }

    @PutMapping("/{study-id}")
    public ResponseEntity<Void> updateStudy(
            @AuthenticatedMember final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody(required = false) final StudyRequest request
    ) {
        studyService.updateStudy(memberId, studyId, request);
        return ResponseEntity.ok().build();
    }
}
