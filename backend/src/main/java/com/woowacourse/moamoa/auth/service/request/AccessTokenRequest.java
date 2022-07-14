package com.woowacourse.moamoa.auth.service.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenRequest {

    @JsonProperty("client_id")
    private final String clientId;

    @JsonProperty("client_secret")
    private final String clientSecret;

    private final String code;

    public AccessTokenRequest(final String clientId, final String clientSecret, final String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCode() {
        return code;
    }
}
