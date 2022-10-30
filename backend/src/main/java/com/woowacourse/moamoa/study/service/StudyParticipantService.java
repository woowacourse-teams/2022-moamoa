package com.woowacourse.moamoa.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyParticipantService {

    private final AsynchronousParticipantService asynchronousParticipantService;

    public synchronized void participateStudy(final Long memberId, final Long studyId) {
        asynchronousParticipantService.participateStudy(memberId, studyId);
    }

    public synchronized void leaveStudy(final Long memberId, final Long studyId) {
        asynchronousParticipantService.leaveStudy(memberId, studyId);
    }

    public synchronized void kickOutMember(final Long ownerId, final Long studyId, final Long participantId) {
        asynchronousParticipantService.kickOutMember(ownerId, studyId, participantId);
    }
}
