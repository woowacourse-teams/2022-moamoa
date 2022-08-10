package com.woowacourse.moamoa.auth.service;

import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.response.TokenResponseWithRefresh;
import com.woowacourse.moamoa.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final OAuthClient oAuthClient;

    @Transactional
    public TokenResponseWithRefresh createToken(final String code) {
        final String accessToken = oAuthClient.getAccessToken(code);
        final GithubProfileResponse githubProfileResponse = oAuthClient.getProfile(accessToken);

        memberService.saveOrUpdate(githubProfileResponse.toMember());

        return tokenProvider.createToken(githubProfileResponse.getGithubId());
    }

    public TokenResponseWithRefresh refreshToken(final Long githubId, final String refreshToken) {
        String accessToken = tokenProvider.recreationAccessToken(githubId, refreshToken);

        return new TokenResponseWithRefresh(accessToken, refreshToken);
    }
}
