package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationExtractor;
import com.woowacourse.moamoa.auth.controller.matcher.AuthenticationRequestMatcher;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final AuthenticationRequestMatcher authenticationRequestMatcher;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        
        if (isPreflight(request)) {
            return true;
        }

        if (authenticationRequestMatcher.isRequiredAuth(request)) {
            final String token = AuthenticationExtractor.extract(request);
            validateToken(token, request.getRequestURI());

            request.setAttribute("payload", tokenProvider.getPayload(token));
        }

        return true;
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    private void validateToken(String token, String requestURI) {
        if (requestURI.equals("/api/auth/refresh") && token != null) {
            return;
        }
        if (token == null || !tokenProvider.validateToken(token)) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        }
    }
}
