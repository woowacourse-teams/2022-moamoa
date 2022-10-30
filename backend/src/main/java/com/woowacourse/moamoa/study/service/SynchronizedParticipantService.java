package com.woowacourse.moamoa.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SynchronizedParticipantService {

    private final StudyParticipantService studyParticipantService;

    public synchronized void participateStudy(final Long memberId, final Long studyId) {
        studyParticipantService.participateStudy(memberId, studyId);
    }

    public synchronized void leaveStudy(final Long memberId, final Long studyId) {
        studyParticipantService.leaveStudy(memberId, studyId);
    }

    public synchronized void kickOutMember(final Long ownerId, final Long studyId, final Long participantId) {
        studyParticipantService.kickOutMember(ownerId, studyId, participantId);
    }
}
