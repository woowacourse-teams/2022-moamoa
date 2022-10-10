package com.woowacourse.acceptance;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.model.Attachment;
import com.woowacourse.acceptance.steps.Steps;
import com.woowacourse.moamoa.MoamoaApplication;
import com.woowacourse.moamoa.alarm.SlackUserProfile;
import com.woowacourse.moamoa.alarm.request.SlackMessageRequest;
import com.woowacourse.moamoa.alarm.response.SlackUserResponse;
import com.woowacourse.moamoa.alarm.response.SlackUsersResponse;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.request.AccessTokenRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
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

    protected MockRestServiceServer mockServer;
    protected String slackUsersUri;
    protected String slackSendMessageUri;
    protected String slackAuthorization;

    @BeforeEach
    protected void setRestDocumentation(
            @Value("${slack.users}") final String slackUsersUri,
            @Value("${slack.send.message}") final String slackSendMessageUri,
            @Value("${slack.authorization}") final String slackAuthorization,
            RestDocumentationContextProvider restDocumentation
    ) {
        this.slackUsersUri = slackUsersUri;
        this.slackSendMessageUri = slackSendMessageUri;
        this.slackAuthorization = slackAuthorization;
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
        Steps.mockServer = mockServer;
        Steps.clientId = clientId;
        Steps.clientSecret = clientSecret;
        Steps.objectMapper = objectMapper;
        Steps.clearTokenCaches();
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.update("TRUNCATE TABLE member");
        jdbcTemplate.update("TRUNCATE TABLE study_tag");
        jdbcTemplate.update("TRUNCATE TABLE study_member");
        jdbcTemplate.update("TRUNCATE TABLE review");
        jdbcTemplate.update("TRUNCATE TABLE study");
        jdbcTemplate.update("TRUNCATE TABLE link");
        jdbcTemplate.update("TRUNCATE TABLE article");
        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY TRUE");
        jdbcTemplate.update("ALTER TABLE member AUTO_INCREMENT = 1");
        jdbcTemplate.update("ALTER TABLE study AUTO_INCREMENT = 1");
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

    protected void mockingSlackAlarm(SlackMessageRequest slackMessageRequest) {
        final SlackUsersResponse slackUsersResponse = new SlackUsersResponse(List.of(
                new SlackUserResponse("green", new SlackUserProfile("greenlawn@moamoa.space")),
                new SlackUserResponse("jjanggu", new SlackUserProfile("jjanggu@moamoa.space")),
                new SlackUserResponse("verus", new SlackUserProfile("verus@moamoa.space")),
                new SlackUserResponse("dwoo", new SlackUserProfile("dwoo@moamoa.space")))
        );

        try {
            mockServer.expect(requestTo(slackUsersUri))
                    .andExpect(method(HttpMethod.GET))
                    .andExpect(header("Authorization", slackAuthorization))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(objectMapper.writeValueAsString(slackUsersResponse)));

            mockServer.expect(requestTo(slackSendMessageUri))
                    .andExpect(method(HttpMethod.POST))
                    .andExpect(content().json(objectMapper.writeValueAsString(slackMessageRequest)))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
