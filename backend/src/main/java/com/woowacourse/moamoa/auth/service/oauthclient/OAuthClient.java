package com.woowacourse.moamoa.auth.service.oauthclient;

import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;

public interface OAuthClient {

    String getAccessToken(final String code);
    GithubProfileResponse getProfile(final String accessToken);
}
