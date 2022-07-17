package com.woowacourse.moamoa.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.moamoa.MoamoaApplication;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.response.OAuthAccessTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = {MoamoaApplication.class}
)
class OAuthClientTest {

    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuthClient oAuthClient;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @DisplayName("Authorization code를 받아서 access token을 발급한다.")
    @Test
    void getAccessToken() throws JsonProcessingException {
        final OAuthAccessTokenResponse token = new OAuthAccessTokenResponse("jwt-token", "", "");

        mockServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(token)));

        final String accessToken = oAuthClient.getAccessToken("code");

        mockServer.verify();

        assertThat(token.getAccessToken()).isEqualTo(accessToken);
    }

    @DisplayName("token을 받아서 사용자 프로필을 조회한다.")
    @Test
    void getProfile() throws JsonProcessingException {
        final GithubProfileResponse profileResponse = new GithubProfileResponse(1L, "sc0116", "https://image", "github.com");

        mockServer.expect(requestTo("https://api.github.com/user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(profileResponse)));

        final GithubProfileResponse response = oAuthClient.getProfile("token");

        mockServer.verify();

        assertThat(profileResponse).usingRecursiveComparison()
                .isEqualTo(response);
    }
}
