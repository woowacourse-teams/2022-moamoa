package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.alarm.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.SlackUsersClient;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsyncService {

    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final SlackUsersClient slackUsersClient;
    private final SlackAlarmSender slackAlarmSender;

    public AsyncService(final StudyRepository studyRepository, final MemberRepository memberRepository,
                        final SlackUsersClient slackUsersClient, final SlackAlarmSender slackAlarmSender) {
        this.studyRepository = studyRepository;
        this.memberRepository = memberRepository;
        this.slackUsersClient = slackUsersClient;
        this.slackAlarmSender = slackAlarmSender;
    }

    @Transactional(readOnly = true)
    public String getOwnerEmail(final Long studyId) {
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Member member = memberRepository.findById(study.getParticipants().getOwnerId())
                .orElseThrow(MemberNotFoundException::new);
        return member.getEmail();
    }

    @Async
    public void  send(final Long studyId) {
        final String email = getOwnerEmail(studyId);
        final String channel = slackUsersClient.getUserChannelByEmail(email);
        slackAlarmSender.requestSlackMessage(channel);
    }
}
