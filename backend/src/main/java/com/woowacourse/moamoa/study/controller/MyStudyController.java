package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.study.service.MyStudyService;
import com.woowacourse.moamoa.study.service.response.MyRoleResponse;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyStudyController {

    private final MyStudyService myStudyService;

    @GetMapping("/api/my/studies")
    public ResponseEntity<MyStudiesResponse> getMyStudies(@AuthenticationPrincipal final Long githubId) {
        final MyStudiesResponse myStudiesResponse = myStudyService.getStudies(githubId);
        return ResponseEntity.ok().body(myStudiesResponse);
    }

    @GetMapping("/api/members/me/role")
    public ResponseEntity<MyRoleResponse> getMyRoleInStudy(
            @AuthenticationPrincipal final Long githubId, @RequestParam(name = "study-id") final Long studyId
    ) {
        return ResponseEntity.ok().body(myStudyService.findMyRoleInStudy(githubId, studyId));
    }
}
