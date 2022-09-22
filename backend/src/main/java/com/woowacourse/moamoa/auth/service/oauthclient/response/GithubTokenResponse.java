package com.woowacourse.moamoa.auth.service.oauthclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GithubTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
