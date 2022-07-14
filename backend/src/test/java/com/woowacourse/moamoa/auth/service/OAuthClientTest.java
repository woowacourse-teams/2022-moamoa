package com.woowacourse.moamoa.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

class OAuthClientTest {

    private OAuthClient oAuthClient;

    @BeforeEach
    void setUp() {
        oAuthClient = Mockito.mock(OAuthClient.class);
    }

    @DisplayName("Authorization code를 주면 Access Token을 반환한다.")
    @Test
    void getAccessToken() {
        Mockito.when(oAuthClient.getAccessToken("dummy"))
                        .thenReturn("access-token");

        assertThat(oAuthClient.getAccessToken("dummy")).isEqualTo("access-token");
    }
}
