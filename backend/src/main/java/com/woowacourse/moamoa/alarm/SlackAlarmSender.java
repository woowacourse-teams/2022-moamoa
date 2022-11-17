package com.woowacourse.moamoa.alarm;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.slack.api.model.Attachment;
import com.woowacourse.moamoa.alarm.request.SlackMessageRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackAlarmSender implements AlarmSender {

    private final String authorization;
    private final String sendMessageUri;
    private final RestTemplate restTemplate;

    public SlackAlarmSender(@Value("${slack.authorization}") final String authorization,
                            @Value("${slack.send.message}") final String sendMessageUri,
                            final RestTemplate restTemplate) {
        this.authorization = authorization;
        this.sendMessageUri = sendMessageUri;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendMessage(final String channel) {
        final SlackMessageRequest slackMessageRequest = setSlackMessage(channel);

        HttpEntity<SlackMessageRequest> request = new HttpEntity<>(slackMessageRequest, headers());
        final ResponseEntity<?> response = restTemplate
                .exchange(sendMessageUri, POST, request, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("슬랙 알림 전송을 실패했습니다.");
        }
    }

    private SlackMessageRequest setSlackMessage(final String channel) {
        return new SlackMessageRequest(channel,
                List.of(Attachment.builder().title("📚 스터디에 새로운 크루가 참여했습니다.")
                        .text("<https://moamoa.space/my/study/|모아모아 바로가기>")
                        .color("#36288f").build()));
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, authorization);
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return headers;
    }
}
