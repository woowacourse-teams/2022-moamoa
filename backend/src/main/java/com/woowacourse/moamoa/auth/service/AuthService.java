package com.woowacourse.moamoa.auth.service;

import com.woowacourse.moamoa.auth.domain.Token;
import com.woowacourse.moamoa.auth.domain.repository.TokenRepository;
import com.woowacourse.moamoa.auth.exception.TokenNotFoundException;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.response.AccessTokenResponse;
import com.woowacourse.moamoa.auth.service.response.TokensResponse;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.MemberService;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import java.util.Optional;
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
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TokensResponse createToken(final String code) {
        final String accessToken = oAuthClient.getAccessToken(code);
        final GithubProfileResponse githubProfileResponse = oAuthClient.getProfile(accessToken);
        memberService.saveOrUpdate(githubProfileResponse.toMember());

        final Member member = memberRepository.findByGithubId(githubProfileResponse.getGithubId())
                .orElseThrow();

        final Optional<Token> token = tokenRepository.findByGithubId(member.getGithubId());
        final TokensResponse tokenResponse = tokenProvider.createToken(member.getId());

        if (token.isPresent()) {
            token.get().updateRefreshToken(tokenResponse.getRefreshToken());
            return tokenResponse;
        }

        tokenRepository.save(new Token(member.getGithubId(), tokenResponse.getRefreshToken()));

        return tokenResponse;
    }

    public AccessTokenResponse refreshToken(final Long githubId, final String refreshToken) {
        final Token token = tokenRepository.findByGithubId(githubId)
                .orElseThrow(TokenNotFoundException::new);

        if (!token.getRefreshToken().equals(refreshToken)) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        }

        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);

        String accessToken = tokenProvider.recreationAccessToken(member.getId(), refreshToken);

        return new AccessTokenResponse(accessToken, tokenProvider.getValidityInMilliseconds());
    }

    @Transactional
    public void logout(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final Token token = tokenRepository.findByGithubId(member.getGithubId())
                .orElseThrow(TokenNotFoundException::new);

        tokenRepository.delete(token);
    }

    public long getExpireTime() {
        return tokenProvider.getValidityInMilliseconds();
    }
}
