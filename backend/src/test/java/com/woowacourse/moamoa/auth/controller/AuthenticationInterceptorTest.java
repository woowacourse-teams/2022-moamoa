package com.woowacourse.moamoa.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;

class AuthenticationInterceptorTest extends WebMVCTest {

    @MockBean
    protected HttpServletRequest httpServletRequest;

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
        final String token = tokenProvider.createToken(1L).getAccessToken();
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
