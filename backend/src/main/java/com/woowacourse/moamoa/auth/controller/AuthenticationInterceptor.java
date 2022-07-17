package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationExtractor;
import com.woowacourse.moamoa.auth.infrastructure.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler
    ) throws Exception {

        if (isPreflight(request)) {
            return true;
        }

        String token = AuthenticationExtractor.extract(request);
        validateToken(token);

        request.setAttribute("payload", jwtTokenProvider.getPayload(token));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    private void validateToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("토큰이 존재하지 않습니다.");
        }
    }
}
