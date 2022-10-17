package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.domain.Participant;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyParticipantService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final DateTimeSystem dateTimeSystem;

    public void participateStudy(final Long memberId, final Long studyId) {
        memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        study.participate(memberId);
    }

    public void leaveStudy(final Long memberId, final Long studyId) {
        memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        final LocalDate now = dateTimeSystem.now().toLocalDate();
        study.leave(new Participant(memberId), now);
    }
}
