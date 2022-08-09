package com.woowacourse.moamoa.community.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.community.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final TestService testService;

    @PostMapping("/api/studies/{study-id}/community/articles")
    public ResponseEntity<Void> createArticle(
            @AuthenticationPrincipal final Long githubId,
            @PathVariable("study-id") final Long studyId
    ) {
        return ResponseEntity.ok().build();
    }
}
