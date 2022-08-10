package com.woowacourse.moamoa.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.moamoa.auth.domain.Token;
import com.woowacourse.moamoa.auth.domain.repository.TokenRepository;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.service.MemberService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {

    private AuthService authService;

    private OAuthClient oAuthClient;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        oAuthClient = Mockito.mock(OAuthClient.class);
        authService = new AuthService(memberService, tokenProvider, oAuthClient, tokenRepository);
    }

    @DisplayName("RefreshToken 을 저장한다.")
    @Test
    public void saveRefreshToken() {
        Mockito.when(oAuthClient.getAccessToken("authorization-code")).thenReturn("access-token");
        Mockito.when(oAuthClient.getProfile("access-token"))
                .thenReturn(new GithubProfileResponse(1L, "dwoo", "imageUrl", "profileUrl"));

        authService.createToken("authorization-code");
        final Token token = tokenRepository.findByGithubId(1L).get();

        assertThat(token.getRefreshToken()).isNotNull();
    }

    @DisplayName("RefreshToken 을 이용하여 AccessToken을 업데이트한다.")
    @Test
    public void updateRefreshToken() {
        Mockito.when(oAuthClient.getAccessToken("authorization-code")).thenReturn("access-token");
        Mockito.when(oAuthClient.getAccessToken("authorizationCode")).thenReturn("accessToken");
        Mockito.when(oAuthClient.getProfile("access-token"))
                .thenReturn(new GithubProfileResponse(1L, "dwoo", "imageUrl", "profileUrl"));
        Mockito.when(oAuthClient.getProfile("accessToken"))
                .thenReturn(new GithubProfileResponse(1L, "dwoo", "imageUrl", "profileUrl"));

        authService.createToken("authorization-code");
        final Token token = tokenRepository.findByGithubId(1L).get();
        final String refreshToken = token.getRefreshToken();

        final TokenResponse tokenResponse = authService.refreshToken(1L, refreshToken);

        assertThat(refreshToken).isNotBlank();
        assertThat(tokenResponse.getAccessToken()).isNotBlank();
    }

    @DisplayName("DB에 저장되어 있지 않은 refresh token으로 access token을 발급받을 수 없다.")
    @Test
    public void validateRefreshToken() {
        Mockito.when(oAuthClient.getAccessToken("authorization-code")).thenReturn("access-token");
        Mockito.when(oAuthClient.getProfile("access-token"))
                .thenReturn(new GithubProfileResponse(1L, "dwoo", "imageUrl", "profileUrl"));

        assertThatThrownBy(() -> authService.refreshToken(1L, "InvalidRefreshToken"))
                .isInstanceOf(UnauthorizedException.class);
    }

    @DisplayName("refresh token을 통해 access token을 발급받을 수 있다.")
    @Test
    public void recreationAccessToken() {
        Mockito.when(oAuthClient.getAccessToken("authorization-code")).thenReturn("access-token");
        Mockito.when(oAuthClient.getProfile("access-token"))
                .thenReturn(new GithubProfileResponse(1L, "dwoo", "imageUrl", "profileUrl"));

        authService.createToken("authorization-code");
        final Token token = tokenRepository.findByGithubId(1L).get();

        assertDoesNotThrow(() -> authService.refreshToken(1L, token.getRefreshToken()));
    }

    @DisplayName("로그아웃을 하면 Token 을 제거한다.")
    @Test
    public void logout() {
        Mockito.when(oAuthClient.getAccessToken("authorization-code")).thenReturn("access-token");
        Mockito.when(oAuthClient.getProfile("access-token"))
                .thenReturn(new GithubProfileResponse(1L, "dwoo", "imageUrl", "profileUrl"));

        authService.createToken("authorization-code");
        final Token token = tokenRepository.findByGithubId(1L).get();

        authService.logout(token.getGithubId());

        final Optional<Token> foundToken = tokenRepository.findByGithubId(token.getGithubId());

        assertThat(token).isNotNull();
        assertThat(foundToken.isEmpty()).isTrue();
    }
}
