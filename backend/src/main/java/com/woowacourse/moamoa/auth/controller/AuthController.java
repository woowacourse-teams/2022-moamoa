package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.auth.service.AuthService;
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
    private static final String ACCESS_TOKEN = "accessToken";

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestParam final String code) {
        final TokensResponse tokenResponse = authService.createToken(code);

        final ResponseCookie accessCookie = putInCookie(ACCESS_TOKEN, tokenResponse.getAccessToken(),
                tokenResponse.getAccessExpireLength());
        final ResponseCookie refreshCookie = putInCookie(REFRESH_TOKEN, tokenResponse.getRefreshToken(),
                tokenResponse.getRefreshExpireLength());

        return ResponseEntity.ok()
                .header("Set-Cookie", accessCookie.toString(), refreshCookie.toString())
                .build();
    }

    @GetMapping("/api/auth/refresh")
    public ResponseEntity<AccessTokenResponse> refreshToken(@AuthenticationPrincipal Long githubId,
                                                            @CookieValue String refreshToken) {
        final AccessTokenResponse response = authService.refreshToken(githubId, refreshToken);
        final ResponseCookie accessCookie = putInCookie(ACCESS_TOKEN, response.getAccessToken(),
                response.getExpiredTime());

        return ResponseEntity.ok().header("Set-Cookie", accessCookie.toString()).build();
    }

    @DeleteMapping("/api/auth/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Long githubId) {
        authService.logout(githubId);

        return ResponseEntity.noContent()
                .header("Set-Cookie", removeCookie(REFRESH_TOKEN).toString(), removeCookie(ACCESS_TOKEN).toString())
                .build();
    }

    private ResponseCookie putInCookie(final String cookieName, final String cookieValue, final long cookieAge) {
        return ResponseCookie.from(cookieName, cookieValue)
                .maxAge(cookieAge)
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
