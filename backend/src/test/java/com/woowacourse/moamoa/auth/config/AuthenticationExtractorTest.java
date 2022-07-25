package com.woowacourse.moamoa.auth.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class AuthenticationExtractorTest {

    @DisplayName("jwt-token을 받았을 때 payload를 반환한다.")
    @Test
    void getPayload() {
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer token");

        final String expected = AuthenticationExtractor.extract(mockHttpServletRequest);

        assertThat(expected).isEqualTo("token");
    }
}
