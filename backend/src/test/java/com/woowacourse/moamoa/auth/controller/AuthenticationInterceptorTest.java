package com.woowacourse.moamoa.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@SpringBootTest
class AuthenticationInterceptorTest {

    @MockBean
    private HttpServletRequest httpServletRequest;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    private TokenProvider tokenProvider;

    @DisplayName("Preflight 요청인지 확인한다.")
    @Test
    void isPreflightRequest() {
        given(httpServletRequest.getMethod())
                .willReturn(HttpMethod.OPTIONS.toString());

        assertThat(authenticationInterceptor.preHandle(httpServletRequest, null, null)).isTrue();
    }

    @DisplayName("유효한 토큰을 검증한다.")
    @Test
    void validateValidToken() {
        final String token = tokenProvider.createToken(1L);
        String bearerToken = "Bearer " + token;

        given(httpServletRequest.getMethod())
                .willReturn(HttpMethod.POST.toString());
        given(httpServletRequest.getRequestURI())
                .willReturn("/api/studies");
        given(httpServletRequest.getHeaders(HttpHeaders.AUTHORIZATION))
                .willReturn(Collections.enumeration(List.of(bearerToken)));

        given(httpServletRequest.getAttribute("payload")).willReturn("1");

        assertThat(authenticationInterceptor.preHandle(httpServletRequest, null, null)).isTrue();
        assertThat(httpServletRequest.getAttribute("payload")).isEqualTo(tokenProvider.getPayload(token));
    }

    @DisplayName("유효하지 않은 토큰일 경우 예외를 던진다.")
    @Test
    void validateInvalidToken() {
        String token = "Bearer " + "Invalid token";

        given(httpServletRequest.getMethod())
                .willReturn(HttpMethod.POST.toString());
        given(httpServletRequest.getRequestURI())
                .willReturn("/api/studies");
        given(httpServletRequest.getHeaders(HttpHeaders.AUTHORIZATION))
                .willReturn(Collections.enumeration(List.of(token)));

        assertThatThrownBy(() -> authenticationInterceptor.preHandle(httpServletRequest, null, null))
                .isInstanceOf(UnauthorizedException.class)
                        .hasMessageContaining("유효하지 않은 토큰입니다.");
    }
}
