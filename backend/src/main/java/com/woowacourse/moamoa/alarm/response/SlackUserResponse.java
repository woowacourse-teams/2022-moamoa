package com.woowacourse.moamoa.alarm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.moamoa.alarm.SlackUserProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class SlackUserResponse {

    @JsonProperty("id")
    private String channel;

    @JsonProperty("profile")
    private SlackUserProfile slackUserProfile;

    public SlackUserResponse(final String channel, final SlackUserProfile slackUserProfile) {
        this.channel = channel;
        this.slackUserProfile = slackUserProfile;
    }
}
