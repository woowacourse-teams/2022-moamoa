package com.woowacourse.moamoa.alarm.service.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class SlackUserProfile {

    private String email;

    public SlackUserProfile(final String email) {
        this.email = email;
    }
}
