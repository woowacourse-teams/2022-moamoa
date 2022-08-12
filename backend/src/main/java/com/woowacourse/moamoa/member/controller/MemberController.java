package com.woowacourse.moamoa.member.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @RequestMapping("/api/members/me")
    public ResponseEntity<MemberResponse> getCurrentMember(
            @AuthenticationPrincipal Long githubId
    ) {
        MemberResponse response = memberService.getByGithubId(githubId);
        return ResponseEntity.ok().body(response);
    }
}
