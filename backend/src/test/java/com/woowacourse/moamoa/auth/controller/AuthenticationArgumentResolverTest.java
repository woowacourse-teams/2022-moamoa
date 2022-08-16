package com.woowacourse.moamoa.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.woowacourse.moamoa.WebMVCTest;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AuthenticationArgumentResolverTest extends WebMVCTest {

    @Autowired
    private AuthenticationArgumentResolver authenticationArgumentResolver;

    @DisplayName("Authorization 인증 타입이 Bearer이 아닌 경우 예외가 발생한다.")
    @Test
    void validAuthorizationTypeIsBearer() {
        String wrongBearerToken = "Basic " + tokenProvider.createToken(1L);
        final Vector<String> cookies = new Vector<>();
        cookies.add(ACCESS_TOKEN + "=" + wrongBearerToken);

        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class))
                .willReturn(httpServletRequest);
        given(httpServletRequest.getHeaders("Cookie"))
                .willReturn(cookies.elements());

        assertThatThrownBy(
                () -> authenticationArgumentResolver.resolveArgument(null, null, nativeWebRequest, null))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("인증 타입이 올바르지 않습니다.");
    }

    @DisplayName("Authorization 인증 타입이 Bearer인 경우 payload를 반환한다.")
    @Test
    void getToken() {
        String wrongBearerToken = "Bearer " + tokenProvider.createToken(1L).getAccessToken();
        final Vector<String> cookies = new Vector<>();
        cookies.add(ACCESS_TOKEN + "=" + wrongBearerToken);

        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class))
                .willReturn(httpServletRequest);
        given(httpServletRequest.getHeaders("Cookie"))
                .willReturn(cookies.elements());

        assertThat(authenticationArgumentResolver.resolveArgument(null, null, nativeWebRequest, null))
                .isEqualTo(1L);
    }
}
