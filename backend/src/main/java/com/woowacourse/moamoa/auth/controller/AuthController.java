package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.auth.config.AuthenticatedRefresh;
import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.response.AccessTokenResponse;
import com.woowacourse.moamoa.auth.service.response.TokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final int REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60;

    private final AuthService authService;
    private final OAuthClient oAuthClient;

    @PostMapping("/api/auth/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestParam final String code) {
        final GithubProfileResponse profile = oAuthClient.getProfile(code);
        final TokensResponse tokenResponse = authService.createToken(profile);

        final AccessTokenResponse response = new AccessTokenResponse(tokenResponse.getAccessToken(), authService.getExpireTime());
        final ResponseCookie cookie = putTokenInCookie(tokenResponse);

        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(response);
    }

    @GetMapping("/api/auth/refresh")
    public ResponseEntity<AccessTokenResponse> refreshToken(@AuthenticatedRefresh Long memberId, @CookieValue String refreshToken) {
        return ResponseEntity.ok().body(authService.refreshToken(memberId, refreshToken));
    }

    @DeleteMapping("/api/auth/logout")
    public ResponseEntity<Void> logout(@AuthenticatedMemberId Long memberId) {
        authService.logout(memberId);

        return ResponseEntity.noContent().header("Set-Cookie", removeCookie().toString()).build();
    }

    private ResponseCookie putTokenInCookie(final TokensResponse tokenResponse) {
        return ResponseCookie.from(REFRESH_TOKEN, tokenResponse.getRefreshToken())
                .maxAge(REFRESH_TOKEN_EXPIRATION)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();
    }

    private ResponseCookie removeCookie() {
        return ResponseCookie.from(REFRESH_TOKEN, null)
                .maxAge(0)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();
    }
}
