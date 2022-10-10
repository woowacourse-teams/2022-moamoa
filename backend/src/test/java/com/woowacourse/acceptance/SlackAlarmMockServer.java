package com.woowacourse.acceptance;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.moamoa.alarm.SlackUserProfile;
import com.woowacourse.moamoa.alarm.request.SlackMessageRequest;
import com.woowacourse.moamoa.alarm.response.SlackUserResponse;
import com.woowacourse.moamoa.alarm.response.SlackUsersResponse;
import com.woowacourse.moamoa.auth.config.AuthConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Import({AuthConfig.class, MockServiceServerResetter.class})
public class SlackAlarmMockServer {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper= new ObjectMapper();

    @Value("${slack.users}")
    protected String slackUsersUri;

    @Value("${slack.send.message}")
    protected String slackSendMessageUri;

    @Value("${slack.authorization}")
    protected String slackAuthorization;

    public MockRestServiceServer mockServer;

    public void sendAlarm(SlackMessageRequest slackMessageRequest) {
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

    public void resetMockServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
}
