package com.woowacourse.moamoa.member.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.member.service.MemberService;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/members/me")
    public ResponseEntity<MemberResponse> getCurrentMember(
            @AuthenticatedMember Long memberId
    ) {
        MemberResponse response = memberService.getByMemberId(memberId);
        return ResponseEntity.ok().body(response);
    }
}
