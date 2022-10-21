package com.woowacourse.acceptance.steps;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.acceptance.SlackAlarmMockServer;
import com.woowacourse.acceptance.document.Document;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.request.AccessTokenRequest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

public abstract class Steps<S extends Steps<S, D>, D extends Document> {

    protected final static Map<Long, String> tokenCache = new HashMap<>();

    public static String clientId;
    public static MockRestServiceServer mockServer;
    public static SlackAlarmMockServer slackAlarmMockServer;
    public static String clientSecret;
    public static ObjectMapper objectMapper;

    protected RequestSpecification spec;

    protected Steps() {
        spec = RestAssured.given();
    }

    @SuppressWarnings("unchecked")
    public S API_문서화를_하고(D document) {
        spec = document.spec();
        return (S) this;
    }

    protected static void mockingGithubServer(String authorizationCode, GithubProfileResponse response) {
        try {
            mockingGithubServerForGetAccessToken(authorizationCode, Map.of("access_token", "access-token",
                    "token_type", "bearer",
                    "scope", ""));
            mockingGithubServerForGetProfile("access-token", HttpStatus.OK, response);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    private static void mockingGithubServerForGetAccessToken(final String authorizationCode,
                                                               final Map<String, String> accessTokenResponse)
            throws JsonProcessingException {
        mockServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new AccessTokenRequest(clientId, clientSecret, authorizationCode))))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(accessTokenResponse)));
    }

    private static void mockingGithubServerForGetProfile(final String accessToken, final HttpStatus status,
                                                           final GithubProfileResponse response)
            throws JsonProcessingException {

        mockServer.expect(requestTo("https://api.github.com/user"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "token " + accessToken))
                .andRespond(withStatus(status)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(response)));
    }

    public static void clearTokenCaches() {
        tokenCache.clear();
    }
}
