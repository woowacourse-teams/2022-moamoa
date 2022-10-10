package com.woowacourse.moamoa.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.woowacourse.acceptance.AcceptanceTest;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;

class OAuthClientTest extends AcceptanceTest {

    @Autowired
    private OAuthClient oAuthClient;

    @DisplayName("token을 받아서 사용자 프로필을 조회한다.")
    @Test
    void getProfile() throws JsonProcessingException {
        final Map<String, String> accessTokenResponse = Map.of("access_token", "access-token", "token_type", "bearer", "scope", "");
        final GithubProfileResponse profileResponse = new GithubProfileResponse(1L, "sc0116",
                "sc0116@moamoa.space", "https://image", "github.com");

        mockServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(accessTokenResponse)));

        mockServer.expect(requestTo("https://api.github.com/user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(profileResponse)));

        final GithubProfileResponse response = oAuthClient.getProfile("code");

        mockServer.verify();

        assertThat(profileResponse).usingRecursiveComparison()
                .isEqualTo(response);
    }
}
