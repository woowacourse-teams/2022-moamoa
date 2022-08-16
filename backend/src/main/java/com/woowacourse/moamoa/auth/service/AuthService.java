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
        memberService.saveOrUpdate(githubProfileResponse.toMember());

        final Long githubId = githubProfileResponse.getGithubId();
        final Optional<Token> token = tokenRepository.findByGithubId(githubId);

        final TokensResponse tokenResponse = tokenProvider.createToken(githubProfileResponse.getGithubId());

        if (token.isPresent()) {
            token.get().updateRefreshToken(tokenResponse.getRefreshToken());
            return tokenResponse;
        }

        tokenRepository.save(new Token(githubProfileResponse.getGithubId(), tokenResponse.getRefreshToken()));

        return tokenResponse;
    }

    public AccessTokenResponse refreshToken(final Long githubId, final String refreshToken) {
        final Token token = tokenRepository.findByGithubId(githubId)
                .orElseThrow(TokenNotFoundException::new);

        if (!token.getRefreshToken().equals(refreshToken)) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        }

        String accessToken = tokenProvider.recreationAccessToken(githubId, refreshToken);

        return new AccessTokenResponse(accessToken, tokenProvider.getAccessExpireLength());
    }

    @Transactional
    public void logout(final Long githubId) {
        final Token token = tokenRepository.findByGithubId(githubId)
                .orElseThrow(TokenNotFoundException::new);

        tokenRepository.delete(token);
    }

    public long getExpireTime() {
        return tokenProvider.getAccessExpireLength();
    }
}
