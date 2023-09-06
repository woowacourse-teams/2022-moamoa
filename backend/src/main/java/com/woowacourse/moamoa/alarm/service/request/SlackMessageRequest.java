package com.woowacourse.moamoa.alarm.service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slack.api.model.Attachment;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SlackMessageRequest {

    @JsonProperty("channel")
    private final String userChannel;

    private final List<Attachment> attachments;
}
