package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.study.service.request.CreateStudyRequest;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.service.CreateStudyService;
import java.net.URI;
import javax.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter
public class CreatingStudyController {

    private final CreateStudyService createStudyService;

    @PostMapping("/api/studies")
    public ResponseEntity<Void> createStudy(
            @AuthenticationPrincipal final Long githubId,
            @Valid @RequestBody(required = false) final CreateStudyRequest createStudyRequest
    ) {
        final Study study = createStudyService.createStudy(githubId, createStudyRequest);
        return ResponseEntity.created(URI.create("/api/studies/" + study.getId())).build();
    }
}
