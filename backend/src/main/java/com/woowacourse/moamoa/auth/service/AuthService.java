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
import com.woowacourse.moamoa.member.service.MemberService;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
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

    @Transactional
    public TokensResponse createToken(final String code) {
        final String accessToken = oAuthClient.getAccessToken(code);
        final GithubProfileResponse githubProfileResponse = oAuthClient.getProfile(accessToken);
        final MemberResponse memberResponse = memberService.saveOrUpdate(githubProfileResponse.toMember());
        final Long memberId = memberResponse.getId();

        final Optional<Token> token = tokenRepository.findByMemberId(memberId);
        final TokensResponse tokenResponse = tokenProvider.createToken(memberId);

        if (token.isPresent()) {
            token.get().updateRefreshToken(tokenResponse.getRefreshToken());
            return tokenResponse;
        }

        tokenRepository.save(new Token(memberId, tokenResponse.getRefreshToken()));

        return tokenResponse;
    }

    public AccessTokenResponse refreshToken(final Long memberId, final String refreshToken) {
        final Token token = tokenRepository.findByMemberId(memberId)
                .orElseThrow(TokenNotFoundException::new);

        if (!token.getRefreshToken().equals(refreshToken)) {
            throw new UnauthorizedException(String.format("유효하지 않은 토큰[%s]입니다.", token));
        }

        return tokenProvider.recreationAccessToken(memberId, refreshToken);
    }

    @Transactional
    public void logout(final Long memberId) {
        final Token token = tokenRepository.findByMemberId(memberId)
                .orElseThrow(TokenNotFoundException::new);

        tokenRepository.delete(token);
    }
}
