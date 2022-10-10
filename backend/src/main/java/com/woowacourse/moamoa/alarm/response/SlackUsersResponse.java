package com.woowacourse.moamoa.alarm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class SlackUsersResponse {

    @JsonProperty("members")
    private List<SlackUserResponse> responses;

    public SlackUsersResponse(final List<SlackUserResponse> responses) {
        this.responses = responses;
    }
}
