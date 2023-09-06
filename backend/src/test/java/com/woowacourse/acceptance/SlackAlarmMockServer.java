package com.woowacourse.acceptance;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.moamoa.alarm.service.response.SlackUserProfile;
import com.woowacourse.moamoa.alarm.service.request.SlackMessageRequest;
import com.woowacourse.moamoa.alarm.service.response.SlackUserResponse;
import com.woowacourse.moamoa.alarm.service.response.SlackUsersResponse;
import com.woowacourse.moamoa.auth.config.AuthConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

@Component
@Import({AuthConfig.class, MockServiceServerResetter.class})
public class SlackAlarmMockServer {

    private final ObjectMapper objectMapper;

    private final MockRestServiceServer mockServer;

    private final String slackUsersUri;

    private final String slackSendMessageUri;

    private final String slackAuthorization;

    @Autowired
    public SlackAlarmMockServer(final ObjectMapper objectMapper,
                                final MockRestServiceServer mockServer,
                                @Value("${slack.users}") final String slackUsersUri,
                                @Value("${slack.send.message}") final String slackSendMessageUri,
                                @Value("${slack.authorization}") final String slackAuthorization
    ) {
        this.objectMapper = objectMapper;
        this.mockServer = mockServer;
        this.slackUsersUri = slackUsersUri;
        this.slackSendMessageUri = slackSendMessageUri;
        this.slackAuthorization = slackAuthorization;
    }

    public void sendAlarm() {
        final SlackUsersResponse slackUsersResponse = new SlackUsersResponse(List.of(
                new SlackUserResponse("user", new SlackUserProfile("user@moamoa.space"))
        ));

        try {
            mockServer.expect(requestTo(slackUsersUri))
                    .andExpect(method(HttpMethod.GET))
                    .andExpect(header("Authorization", slackAuthorization))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(objectMapper.writeValueAsString(slackUsersResponse)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAlarmWithExpect(SlackMessageRequest slackMessageRequest) {
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
        mockServer.reset();
    }
}
