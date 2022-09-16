package com.woowacourse.moamoa.auth.controller.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.WebMVCTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

class PathMatcherContainerTest extends WebMVCTest {

    @Autowired
    private PathMatcherContainer pathMatcherContainer;

    @DisplayName("인증이 필요한 요청이 아닌지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "/api/studies,OPTION,true",
            "/api/studies,POST,false",
            "/api/studies,GET,true",
            "/api/members/me/role,GET,false",
            "/api/studies/1/reviews,POST,false",
            "/api/studies/1/reviews/1,DELETE,false",
            "/api/study/\\d+,PUT,false"
    })
    void checkIsValidateRequest(String path, String method, boolean expected) {
        assertThat(pathMatcherContainer.isNotIncludePath(path, method)).isEqualTo(expected);
    }
}
