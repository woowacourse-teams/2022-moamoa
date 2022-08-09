package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import com.woowacourse.moamoa.auth.service.response.TokenResponseWithRefresh;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/login/token")
    public ResponseEntity<TokenResponse> login(HttpServletResponse response, @RequestParam final String code) {
        TokenResponseWithRefresh tokenResponse = authService.createToken(code);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenResponse.getRefreshToken())
                .maxAge(7 * 24 * 60 * 60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().body(new TokenResponse(tokenResponse.getAccessToken()));
    }

    @GetMapping("/api/auth/refresh")
    public ResponseEntity<TokenResponseWithRefresh> refreshToken(@CookieValue String refreshToken) {
        return ResponseEntity.ok().body(authService.refreshToken(refreshToken));
    }
}
