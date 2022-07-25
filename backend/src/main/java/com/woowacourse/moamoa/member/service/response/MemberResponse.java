package com.woowacourse.moamoa.member.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.moamoa.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberResponse {

    @JsonProperty("id")
    private Long githubId;

    private String username;

    private String imageUrl;

    private String profileUrl;

    public static MemberResponse from(final Member member) {
        return new MemberResponse(
                member.getGithubId(), member.getUsername(), member.getImageUrl(), member.getProfileUrl()
        );
    }
}
