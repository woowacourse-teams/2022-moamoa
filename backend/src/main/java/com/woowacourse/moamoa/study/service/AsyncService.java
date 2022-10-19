package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.alarm.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.SlackUsersClient;
import com.woowacourse.moamoa.member.domain.Member;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    private final SlackUsersClient slackUsersClient;
    private final SlackAlarmSender slackAlarmSender;

    public AsyncService(final SlackUsersClient slackUsersClient, final SlackAlarmSender slackAlarmSender) {
        this.slackUsersClient = slackUsersClient;
        this.slackAlarmSender = slackAlarmSender;
    }

    @Async
    public void send(Member owner) {
        final String channel = slackUsersClient.getUserChannelByEmail(owner.getEmail());
        slackAlarmSender.requestSlackMessage(channel);
    }
}
