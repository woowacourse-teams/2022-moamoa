package com.woowacourse.moamoa.auth.service.response;

public class TokenResponse {

    private final String token;

    public TokenResponse(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
