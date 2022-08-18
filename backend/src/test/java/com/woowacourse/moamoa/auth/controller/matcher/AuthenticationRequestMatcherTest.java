package com.woowacourse.moamoa.auth.controller.matcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.woowacourse.moamoa.MoamoaApplication;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = {MoamoaApplication.class}
)
class AuthenticationRequestMatcherTest {

    @Autowired
    private AuthenticationRequestMatcher authenticationRequestMatcher;

    @MockBean
    private HttpServletRequest httpServletRequest;

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
