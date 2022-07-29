package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationExtractor;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (isPreflight(request)) {
            return true;
        }

        if (request.getMethod().equals("POST") && validatePostPath(request) ||
                request.getMethod().equals("GET") && validateGetPath(request)) {
            final String token = AuthenticationExtractor.extract(request);
            validateToken(token);

            request.setAttribute("payload", tokenProvider.getPayload(token));
        }

        return true;
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    private void validateToken(String token) {
        if (token == null || !tokenProvider.validateToken(token)) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        }
    }

    private boolean validatePostPath(final HttpServletRequest request) {
        return request.getServletPath().equals("/api/studies") ||
                request.getServletPath().matches("/api/studies/\\d+/reviews");
    }

    private boolean validateGetPath(final HttpServletRequest request) {
        return request.getServletPath().equals("/api/my/studies");
    }
}
