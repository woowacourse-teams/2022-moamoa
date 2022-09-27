package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.auth.config.AuthenticatedRefresh;
import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.auth.service.response.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestParam final String code) {
        return ResponseEntity.ok().body(authService.createToken(code));
    }

    @GetMapping("/api/auth/refresh")
    public ResponseEntity<AccessTokenResponse> refresh(@AuthenticatedMemberId Long memberId) {
        return ResponseEntity.ok().body(authService.refreshToken(memberId));
    }
}
