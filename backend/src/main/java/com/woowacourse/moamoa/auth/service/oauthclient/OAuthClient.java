package com.woowacourse.moamoa.auth.service.oauthclient;

import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;

public interface OAuthClient {

    GithubProfileResponse getProfile(final String code);
}
