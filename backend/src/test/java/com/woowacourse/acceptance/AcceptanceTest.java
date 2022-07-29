package com.woowacourse.acceptance;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.moamoa.MoamoaApplication;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.request.AccessTokenRequest;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import io.restassured.RestAssured;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = {MoamoaApplication.class}
)
public class AcceptanceTest {
    @LocalServerPort
    protected int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${oauth2.github.client-id}")
    private String clientId;

    @Value("${oauth2.github.client-secret}")
    private String clientSecret;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @BeforeEach
    void mockingGithubServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM study_tag");
        jdbcTemplate.update("DELETE FROM study_member");
        jdbcTemplate.update("DELETE FROM tag");
        jdbcTemplate.update("DELETE FROM category");
        jdbcTemplate.update("DELETE FROM review");
        jdbcTemplate.update("DELETE FROM study");
        jdbcTemplate.update("DELETE FROM member");

        jdbcTemplate.update("ALTER TABLE member AUTO_INCREMENT = 1");
        jdbcTemplate.update("ALTER TABLE study AUTO_INCREMENT = 1");
    }

    protected String getBearerTokenBySignInOrUp(GithubProfileResponse response) {
        final String authorizationCode = "Authorization Code";
        mockingGithubServer(authorizationCode, response);
        final String token = RestAssured.given().log().all()
                .param("code", authorizationCode)
                .when()
                .post("/api/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getString("token");
        mockServer.reset();
        return "bearer " + token;
    }

    protected long createStudy(String jwtToken, CreatingStudyRequest request) {
        try {
            final String location = RestAssured.given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, jwtToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(objectMapper.writeValueAsString(request))
                    .when().log().all()
                    .post("/api/studies")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().header(HttpHeaders.LOCATION);
            return Long.parseLong(location.replaceAll("/api/studies/", ""));
        } catch (Exception e) {
            Assertions.fail("스터디 생성 실패");
            return -1;
        }
    }

    private void mockingGithubServer(String authorizationCode, GithubProfileResponse response) {
        try {
            mockingGithubServerForGetAccessToken(authorizationCode, Map.of("access_token", "access-token",
                    "token_type", "bearer",
                    "scope", ""));
            mockingGithubServerForGetProfile("access-token", HttpStatus.OK, response);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    protected void mockingGithubServerForGetAccessToken(final String authorizationCode,
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

    protected void mockingGithubServerForGetProfile(final String accessToken, final HttpStatus status,
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
