package com.woowacourse.moamoa.auth.controller.matcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.woowacourse.moamoa.WebMVCTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

class AuthenticationRequestMatcherTest extends WebMVCTest {

    @Autowired
    private AuthenticationRequestMatcher authenticationRequestMatcher;

    @DisplayName("사용자 인증이 필요한 요청인 경우")
    @ParameterizedTest
    @CsvSource(value = {
            "POST,/api/studies", "POST,/api/studies/1/reviews", "GET,/api/my/studies"
    })
    void matchAuthRequestIsTrue(String method, String path) {
        given(httpServletRequest.getMethod())
                .willReturn(method);
        given(httpServletRequest.getRequestURI())
                .willReturn(path);

        assertThat(authenticationRequestMatcher.isRequiredAuth(httpServletRequest)).isTrue();
    }

    @DisplayName("사용자 인증이 필요한 요청이 아닌 경우")
    @ParameterizedTest
    @CsvSource(value = {
            "GET,/api/studies", "GET,/api/studies/1/reviews", "GET,/api/studies/search", "GET,/api/studies/1"
    })
    void matchAuthRequestIsFalse(String method, String path) {
        given(httpServletRequest.getMethod())
                .willReturn(method);
        given(httpServletRequest.getRequestURI())
                .willReturn(path);

        assertThat(authenticationRequestMatcher.isRequiredAuth(httpServletRequest)).isFalse();
    }
}
