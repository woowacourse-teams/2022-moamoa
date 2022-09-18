package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.auth.config.AuthenticatedRefresh;
import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.auth.service.response.AccessTokenResponse;
import com.woowacourse.moamoa.auth.service.response.LoginStatusResponse;
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
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final long COOKIE_EXPIRE_TIME = 604_800_000L;

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestParam final String code) {
        final TokensResponse tokenResponse = authService.createToken(code);

        final ResponseCookie accessCookie = putInCookie(ACCESS_TOKEN, tokenResponse.getAccessToken());
        final ResponseCookie refreshCookie = putInCookie(REFRESH_TOKEN, tokenResponse.getRefreshToken());

        return ResponseEntity.ok()
                .header(SET_COOKIE, accessCookie.toString(), refreshCookie.toString())
                .build();
    }

    @GetMapping("/api/auth/refresh")
    public ResponseEntity<AccessTokenResponse> refreshToken(@AuthenticatedRefresh Long memberId,
                                                            @CookieValue String refreshToken) {
        final TokensResponse response = authService.refreshToken(memberId, refreshToken);
        final ResponseCookie accessCookie = putInCookie(ACCESS_TOKEN, response.getAccessToken());
        final ResponseCookie refreshCookie = putInCookie(REFRESH_TOKEN, response.getRefreshToken());

        return ResponseEntity.ok()
                .header(SET_COOKIE, accessCookie.toString(), refreshCookie.toString())
                .build();
    }

    @GetMapping("/api/auth/login/status")
    public ResponseEntity<LoginStatusResponse> checkLoginStatus(@CookieValue String accessToken) {
        final LoginStatusResponse loginStatusResponse = authService.validateLoginStatus(accessToken);

        return ResponseEntity.ok(loginStatusResponse);
    }

    @DeleteMapping("/api/auth/logout")
    public ResponseEntity<Void> logout(@AuthenticatedMemberId Long memberId) {
        authService.logout(memberId);

        return ResponseEntity.noContent()
                .header(SET_COOKIE, removeCookie(REFRESH_TOKEN).toString(), removeCookie(ACCESS_TOKEN).toString())
                .build();
    }

    private ResponseCookie putInCookie(final String cookieName, final String cookieValue) {
        return ResponseCookie.from(cookieName, cookieValue)
                .maxAge(COOKIE_EXPIRE_TIME)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();
    }

    private ResponseCookie removeCookie(final String cookieName) {
        return ResponseCookie.from(cookieName, null)
                .maxAge(0)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();
    }
}
