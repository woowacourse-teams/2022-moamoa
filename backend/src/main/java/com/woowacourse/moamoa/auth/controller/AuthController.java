package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.auth.service.AuthService;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
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

    @PostMapping("/api/login/token")
    public ResponseEntity<TokenResponse> login(@RequestParam final String code) {
        return ResponseEntity.ok().body(authService.createToken(code));
    }

    @GetMapping("/api/auth/role")
    public ResponseEntity<Void> getStudyRole(
            @AuthenticationPrincipal Long githubId, @RequestParam(name = "study-id") Long studyId) {
        return ResponseEntity.ok().build();
    }
}
