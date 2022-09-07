package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.study.service.MyStudyService;
import com.woowacourse.moamoa.study.service.response.MyRoleResponse;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyStudyController {

    private final MyStudyService myStudyService;

    @GetMapping("/api/my/studies")
    public ResponseEntity<MyStudiesResponse> getMyStudies(@AuthenticatedMember final Long memberId) {
        final MyStudiesResponse myStudiesResponse = myStudyService.getStudies(memberId);
        return ResponseEntity.ok().body(myStudiesResponse);
    }

    @GetMapping("/api/members/me/role")
    public ResponseEntity<MyRoleResponse> getMyRoleInStudy(
            @AuthenticatedMember final Long memberId, @RequestParam(name = "study-id") final Long studyId
    ) {
        return ResponseEntity.ok().body(myStudyService.findMyRoleInStudy(memberId, studyId));
    }
}
