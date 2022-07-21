package com.woowacourse.acceptance.auth;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.request.AccessTokenRequest;
import io.restassured.RestAssured;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

public class AuthAcceptanceTest extends AcceptanceTest {

    @Value("${oauth2.github.client-id}")
    private String clientId;

    @Value("${oauth2.github.client-secret}")
    private String clientSecret;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setMockServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @DisplayName("Authorization code를 받아서 token을 발급한다.")
    @Test
    void getJwtToken() throws JsonProcessingException {
        final String authorizationCode = "Authorization Code";
        final String accessToken = "access-token";
        mockingGithubServerForGetAccessToken(authorizationCode, Map.of("access_token", accessToken,
                "token_type", "bearer",
                "scope", ""));
        mockingGithubServerForGetProfile(accessToken, HttpStatus.OK,
                new GithubProfileResponse(1L, "sc0116", "https://image", "github.com"));

        RestAssured.given().log().all()
                .param("code", authorizationCode)
                .when()
                .post("/api/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("유효하지 않은 Authorization Code인 경우 401을 반환한다.")
    void get401ByInvalidAuthorizationCode() throws JsonProcessingException {
        final Map<String, String> response = Map.of("error", "has error",
                "error_description", "error description",
                "error_uri", "error uri");
        final String invalidAuthorizationCode = "Invalid Authorization Code";
        mockingGithubServerForGetAccessToken(invalidAuthorizationCode, response);

        RestAssured.given().log().all()
                .param("code", invalidAuthorizationCode)
                .when()
                .post("/api/login/token")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("유효하지 않는 Access Token인 경우 401을 반환한다.")
    void get401ByInvalidAccessToken() throws JsonProcessingException {
        final String authorizationCode = "Authorization Code";
        final String accessToken = "access_token";
        final Map<String, String> accessTokenResponse = Map.of(accessToken, "access-token",
                "token_type", "bearer",
                "scope", "");

        mockingGithubServerForGetAccessToken(authorizationCode, accessTokenResponse);
        mockingGithubServerForGetProfile("access-token", HttpStatus.UNAUTHORIZED);

        RestAssured.given().log().all()
                .param("code", authorizationCode)
                .when()
                .post("/api/login/token")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private void mockingGithubServerForGetAccessToken(final String authorizationCode,
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

    private void mockingGithubServerForGetProfile(final String accessToken, final HttpStatus status)
            throws JsonProcessingException {
        mockingGithubServerForGetProfile(accessToken, status, null);
    }

    private void mockingGithubServerForGetProfile(final String accessToken, final HttpStatus status,
                                                  final GithubProfileResponse response)
            throws JsonProcessingException {

        mockServer.expect(requestTo("https://api.github.com/user"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "token " + accessToken))
                .andRespond(withStatus(status)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(response)));
    }
}
