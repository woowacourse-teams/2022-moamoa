package com.woowacourse.moamoa.auth.controller.interceptor;

import com.woowacourse.moamoa.auth.config.AuthenticationExtractor;
import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final String token = AuthenticationExtractor.extract(request);
        validateToken(token);

        request.setAttribute("payload", token);
        return true;
    }

    private void validateToken(final String token) {
        if (token == null || !tokenProvider.validateToken(token)) {
            throw new UnauthorizedException(String.format("유효하지 않은 토큰[%s]입니다.", token));
        }
    }
}
