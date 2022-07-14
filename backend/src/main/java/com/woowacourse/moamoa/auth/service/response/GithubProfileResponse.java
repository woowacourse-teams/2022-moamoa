package com.woowacourse.moamoa.auth.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GithubProfileResponse {

    @JsonProperty("id")
    private Long gitgubId;

    @JsonProperty("login")
    private String username;

    @JsonProperty("avatar_url")
    private String imageUrl;

    @JsonProperty("html_url")
    private String profileUrl;

    public GithubProfileResponse(final Long gitgubId, final String username, final String imageUrl,
                                 final String profileUrl) {
        this.gitgubId = gitgubId;
        this.username = username;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
    }
}
