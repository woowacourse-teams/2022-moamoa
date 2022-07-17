package com.woowacourse.moamoa.auth.service;

import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.auth.service.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import com.woowacourse.moamoa.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuthClient oAuthClient;

    @Transactional
    public TokenResponse createToken(final String code) {
        final String accessToken = oAuthClient.getAccessToken(code);
        final GithubProfileResponse githubProfileResponse = oAuthClient.getProfile(accessToken);

        memberService.saveOrUpdate(githubProfileResponse.toMember());

        final String jwtToken = jwtTokenProvider.createToken(githubProfileResponse.getGitgubId());
        return new TokenResponse(jwtToken);
    }
}
