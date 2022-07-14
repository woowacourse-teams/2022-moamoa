package com.woowacourse.moamoa.auth.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    private String scope;

    private OAuthAccessTokenResponse() {
    }

    public OAuthAccessTokenResponse(
            String accessToken,
            String tokenType,
            String scope
    ) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getScope() {
        return scope;
    }
}
