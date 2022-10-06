package com.woowacourse.moamoa.member.service.response;

import com.woowacourse.moamoa.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberResponse {

    private Long id;
    private String username;
    private String profileUrl;
    private String imageUrl;

    public MemberResponse(Member member) {
        this(member.getId(), member.getUsername(), member.getProfileUrl(), member.getImageUrl());
    }
}
