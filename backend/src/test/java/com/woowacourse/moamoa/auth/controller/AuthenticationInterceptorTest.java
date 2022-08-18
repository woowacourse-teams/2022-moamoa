package com.woowacourse.moamoa.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import java.util.Vector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

class AuthenticationInterceptorTest extends WebMVCTest {

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
        final Vector<String> cookies = new Vector<>();
        cookies.add(ACCESS_TOKEN + "=" + token);

        given(httpServletRequest.getMethod())
                .willReturn(HttpMethod.POST.toString());
        given(httpServletRequest.getRequestURI())
                .willReturn("/api/studies");
        given(httpServletRequest.getHeaders("Cookie"))
                .willReturn(cookies.elements());
        given(httpServletRequest.getAttribute("payload")).willReturn("1");

        assertThat(authenticationInterceptor.preHandle(httpServletRequest, null, null)).isTrue();
        assertThat(httpServletRequest.getAttribute("payload")).isEqualTo(tokenProvider.getPayload(token));
    }

    @DisplayName("유효하지 않은 토큰일 경우 예외를 던진다.")
    @Test
    void validateInvalidToken() {
        String token = "Bearer " + "Invalid token";
        final Vector<String> cookies = new Vector<>();
        cookies.add(ACCESS_TOKEN + "=" + token);

        given(httpServletRequest.getMethod())
                .willReturn(HttpMethod.POST.toString());
        given(httpServletRequest.getRequestURI())
                .willReturn("/api/studies");
        given(httpServletRequest.getHeaders("Cookie"))
                .willReturn(cookies.elements());

        assertThatThrownBy(() -> authenticationInterceptor.preHandle(httpServletRequest, null, null))
                .isInstanceOf(UnauthorizedException.class)
                        .hasMessageContaining("유효하지 않은 토큰입니다.");
    }
}
