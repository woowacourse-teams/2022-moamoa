package com.woowacourse.moamoa.alarm.service;

import com.woowacourse.moamoa.alarm.AlarmSender;
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
public class AlarmService {

    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final SlackUsersClient slackUsersClient;
    private final AlarmSender alarmSender;

    public AlarmService(final StudyRepository studyRepository, final MemberRepository memberRepository,
                        final SlackUsersClient slackUsersClient, final AlarmSender alarmSender) {
        this.studyRepository = studyRepository;
        this.memberRepository = memberRepository;
        this.slackUsersClient = slackUsersClient;
        this.alarmSender = alarmSender;
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
    public void send(final Long studyId) {
        final String email = getOwnerEmail(studyId);
        final String channel = slackUsersClient.getUserChannelByEmail(email);
        alarmSender.sendMessage(channel);
    }
}
