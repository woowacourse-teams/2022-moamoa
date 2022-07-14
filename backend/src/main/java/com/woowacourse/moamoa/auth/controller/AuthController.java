package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/login/token")
    public ResponseEntity<TokenResponse> login(@RequestParam final String code) {
        final TokenResponse token = authService.createToken(code);
        return ResponseEntity.ok().body(token);
    }
}
