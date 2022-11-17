package com.woowacourse.moamoa.alarm;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacourse.moamoa.alarm.service.response.SlackUserResponse;
import com.woowacourse.moamoa.alarm.service.response.SlackUsersResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackUsersClient {

    private final String authorization;
    private final String usersUri;
    private final RestTemplate restTemplate;

    public SlackUsersClient(@Value("${slack.authorization}") final String authorization,
                            @Value("${slack.users}") final String usersUri,
                            final RestTemplate restTemplate) {
        this.authorization = authorization;
        this.usersUri = usersUri;
        this.restTemplate = restTemplate;
    }

    public SlackUsersResponse requestSlackUsers() {
        HttpEntity<SlackUsersResponse> request = new HttpEntity<>(headers());
        final ResponseEntity<SlackUsersResponse> response = restTemplate
                .exchange(usersUri, GET, request, SlackUsersResponse.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("워크스페이스에서 user 목록을 조회하는 것을 실패했습니다.");
        }
        return response.getBody();
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, authorization);
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return headers;
    }

    public String getUserChannelByEmail(final String email) {
        SlackUserResponse slackUser = getSlackUser(email, requestSlackUsers());
        return slackUser.getChannel();
    }

    private SlackUserResponse getSlackUser(final String email, final SlackUsersResponse response) {
        return response.getResponses()
                .stream()
                .filter(slackUser -> email.equals(slackUser.getSlackUserProfile().getEmail()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("슬랙에서 사용자를 찾지 못 했습니다."));
    }
}
