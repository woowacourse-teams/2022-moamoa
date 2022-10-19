package com.woowacourse.moamoa.auth.service.oauthclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.moamoa.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GithubProfileResponse {

    @JsonProperty("id")
    private Long githubId;

    @JsonProperty("login")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("avatar_url")
    private String imageUrl;

    @JsonProperty("html_url")
    private String profileUrl;

    public Member toMember() {
        return new Member(githubId, username, email, imageUrl, profileUrl);
    }
}
