package com.woowacourse.acceptance;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;
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
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
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
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = {MoamoaApplication.class}
)
public class AcceptanceTest {

    private static final String OUTPUT_DIRECTORY = "build/generated-snippets";

    protected RequestSpecification spec;

    @RegisterExtension
    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension (OUTPUT_DIRECTORY);

    @LocalServerPort
    protected int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    @Value("${oauth2.github.client-id}")
    private String clientId;

    @Value("${oauth2.github.client-secret}")
    private String clientSecret;

    private MockRestServiceServer mockServer;

    @BeforeEach
    protected void setRestDocumentation(RestDocumentationContextProvider restDocumentation) {
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @BeforeEach
    protected void setRestAssuredPort() {
        RestAssured.port = port;
    }

    @BeforeEach
    void mockingGithubServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.update("TRUNCATE TABLE member");
        jdbcTemplate.update("TRUNCATE TABLE study_tag");
        jdbcTemplate.update("TRUNCATE TABLE study_member");
        jdbcTemplate.update("TRUNCATE TABLE tag");
        jdbcTemplate.update("TRUNCATE TABLE category");
        jdbcTemplate.update("TRUNCATE TABLE review");
        jdbcTemplate.update("TRUNCATE TABLE study");
        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY TRUE");

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
        return "Bearer " + token;
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

    protected long createReview(String jwtToken, Long studyId, WriteReviewRequest request) {
        try {
            final String location = RestAssured.given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, jwtToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .pathParams("study-id", studyId)
                    .body(objectMapper.writeValueAsString(request))
                    .when().post("/api/studies/{study-id}/reviews")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().header(HttpHeaders.LOCATION);
            return Long.parseLong(location.replaceAll("/api/studies/" + studyId + "/reviews/", ""));
        } catch (Exception e) {
            Assertions.fail("리뷰 작성 실패");
            return -1;
        }
    }

    protected void participateStudy(String jwtToken, Long studyId) {
        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, jwtToken)
                .when().log().all()
                .post("/api/studies/" + studyId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
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
