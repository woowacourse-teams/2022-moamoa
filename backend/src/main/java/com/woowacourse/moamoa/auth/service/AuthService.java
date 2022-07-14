package com.woowacourse.moamoa.auth.service;

import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final OAuthClient oAuthClient;

    public AuthService(
            final MemberRepository memberRepository,
            final JwtTokenProvider jwtTokenProvider,
            final OAuthClient oAuthClient
    ) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.oAuthClient = oAuthClient;
    }

    public TokenResponse createToken(final String code) {
        return new TokenResponse(oAuthClient.getAccessToken(code));
    }
}
