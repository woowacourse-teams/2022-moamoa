package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import com.woowacourse.moamoa.auth.service.response.TokenResponseWithRefresh;
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

    @PostMapping("/api/auth/login")
    public ResponseEntity<TokenResponse> login(@RequestParam final String code) {
        final TokenResponseWithRefresh tokenResponse = authService.createToken(code);

        final TokenResponse response = new TokenResponse(tokenResponse.getAccessToken(), authService.getExpireTime());
        final ResponseCookie cookie = putTokenInCookie(tokenResponse);

        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(response);
    }

    @GetMapping("/api/auth/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@AuthenticationPrincipal Long githubId, @CookieValue String refreshToken) {
        return ResponseEntity.ok().body(authService.refreshToken(githubId, refreshToken));
    }

    @DeleteMapping("/api/auth/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Long githubId) {
        authService.logout(githubId);

        return ResponseEntity.noContent().header("Set-Cookie", removeCookie().toString()).build();
    }

    private static ResponseCookie putTokenInCookie(final TokenResponseWithRefresh tokenResponse) {
        return ResponseCookie.from(REFRESH_TOKEN, tokenResponse.getRefreshToken())
                .maxAge(REFRESH_TOKEN_EXPIRATION)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();
    }

    private static ResponseCookie removeCookie() {
        return ResponseCookie.from(REFRESH_TOKEN, null)
                .maxAge(0)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();
    }
}
