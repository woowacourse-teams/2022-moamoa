package com.woowacourse.moamoa.auth.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationExtractor;
import com.woowacourse.moamoa.auth.controller.matcher.AuthenticationRequestMatcher;
import com.woowacourse.moamoa.auth.controller.matcher.AuthenticationRequestMatcherBuilder;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final AuthenticationRequestMatcher authenticationRequestMatcher;

    public AuthenticationInterceptor(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.authenticationRequestMatcher = new AuthenticationRequestMatcherBuilder()
                .setUpAuthenticationPath(HttpMethod.POST, "/api/studies", "/api/studies/\\d+/reviews")
                .setUpAuthenticationPath(HttpMethod.GET, "/api/my/studies")
                .build();;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (isPreflight(request)) {
            return true;
        }

        if (authenticationRequestMatcher.isRequiredAuth(request)) {
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
}
