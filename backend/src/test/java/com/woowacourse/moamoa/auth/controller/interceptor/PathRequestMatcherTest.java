package com.woowacourse.moamoa.auth.controller.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.auth.config.interceptor.PathRequestMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

class PathRequestMatcherTest {

    @DisplayName("요청이 잘 match되는지 검증한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "/api/studies,POST,/api/studies,POST,true",
            "/api/studies,GET,/api/studies,POST,false",
            "/api/studies/**,POST,/api/studies/1,POST,true",
            "/api/studies/**,PUT,/api/studies/1/reviews/1,PUT,true"
    })
    void isMatch(String path, HttpMethod method, String targetPath, String targetMethod, boolean expected) {
        final PathRequestMatcher pathRequestMatcher = new PathRequestMatcher(path, method);

        assertThat(pathRequestMatcher.match(new AntPathMatcher(), targetPath, targetMethod)).isEqualTo(expected);
    }
}
