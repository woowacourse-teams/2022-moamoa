package com.woowacourse.moamoa.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.woowacourse.moamoa.auth.domain.Token;
import com.woowacourse.moamoa.auth.domain.repository.TokenRepository;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.response.AccessTokenResponse;
import com.woowacourse.moamoa.auth.service.response.TokensResponse;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.service.MemberService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class AuthServiceTest {

    private AuthService authService;

    private OAuthClient oAuthClient;

    private TokenProvider tokenProvider;

    private MemberService memberService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, memberDao);
        tokenProvider = Mockito.mock(TokenProvider.class);
        oAuthClient = Mockito.mock(OAuthClient.class);
        authService = new AuthService(memberService, tokenProvider, oAuthClient, tokenRepository);

        Mockito.when(oAuthClient.getAccessToken("authorization-code")).thenReturn("access-token");
        Mockito.when(oAuthClient.getProfile("access-token"))
                .thenReturn(new GithubProfileResponse(1L, "dwoo", "imageUrl", "profileUrl"));
        Mockito.when(tokenProvider.createToken(any()))
                .thenReturn(new TokensResponse("accessToken", "refreshToken", 1800000, 604800000));
        Mockito.when(tokenProvider.recreationAccessToken(any(), eq("refreshToken")))
                .thenReturn(new AccessTokenResponse("recreationAccessToken", 1800000));
    }

    @DisplayName("RefreshToken 을 저장한다.")
    @Test
    void saveRefreshToken() {
        authService.createToken("authorization-code");
        final Member member = memberRepository.findByGithubId(1L).get();
        final Token token = tokenRepository.findByMemberId(member.getId()).get();

        assertThat(token.getRefreshToken()).isEqualTo("refreshToken");
    }

    @DisplayName("RefreshToken 을 이용하여 AccessToken 을 업데이트한다.")
    @Test
    void updateRefreshToken() {
        authService.createToken("authorization-code");
        final Member member = memberRepository.findByGithubId(1L).get();
        final Token token = tokenRepository.findByMemberId(member.getId()).get();
        final String refreshToken = token.getRefreshToken();

        final AccessTokenResponse accessTokenResponse = authService.refreshToken(member.getId(), refreshToken);
        assertThat(refreshToken).isNotBlank();
        assertThat(accessTokenResponse.getAccessToken()).isEqualTo("recreationAccessToken");
    }

    @DisplayName("DB에 저장되어 있지 않은 refresh token으로 access token을 발급받을 수 없다.")
    @Test
    void validateRefreshToken() {
        assertThatThrownBy(() -> authService.refreshToken(1L, "InvalidRefreshToken"))
                .isInstanceOf(UnauthorizedException.class);
    }

    @DisplayName("refresh token을 통해 access token을 발급받을 수 있다.")
    @Test
    void recreationAccessToken() {
        authService.createToken("authorization-code");
        final Member member = memberRepository.findByGithubId(1L).get();
        final Token token = tokenRepository.findByMemberId(member.getId()).get();

        assertDoesNotThrow(() -> authService.refreshToken(member.getId(), token.getRefreshToken()));
    }

    @DisplayName("로그아웃을 하면 Token 을 제거한다.")
    @Test
    void logout() {
        authService.createToken("authorization-code");
        final Member member = memberRepository.findByGithubId(1L).get();
        final Token token = tokenRepository.findByMemberId(member.getId()).get();

        authService.logout(member.getId());

        final Optional<Token> foundToken = tokenRepository.findByMemberId(token.getMemberId());

        assertThat(token).isNotNull();
        assertThat(foundToken).isEmpty();
    }
}
