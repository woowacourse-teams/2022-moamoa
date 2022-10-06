package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Content;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.RecruitPlanner;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.StudyPlanner;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudyService {

    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final DateTimeSystem dateTimeSystem;

    public StudyService(final StudyRepository studyRepository,
                        final MemberRepository memberRepository,
                        final DateTimeSystem dateTimeSystem
    ) {
        this.studyRepository = studyRepository;
        this.memberRepository = memberRepository;
        this.dateTimeSystem = dateTimeSystem;
    }

    public Study createStudy(final Long memberId, final StudyRequest request) {
        final LocalDateTime createdAt = dateTimeSystem.now();
        final Member owner = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        final Participants participants = request.mapToParticipants(owner.getId());

        final AttachedTags attachedTags = request.mapToAttachedTags();
        final Content content = request.mapToContent();

        return studyRepository.save(
                new Study(content, participants, attachedTags, createdAt, request.getMaxMemberCount(),
                request.getEnrollmentEndDate(), request.getStartDate(), request.getEndDate())
        );
    }

    public void autoUpdateStatus() {
        final List<Study> studies = studyRepository.findAll();
        final LocalDate now = dateTimeSystem.now().toLocalDate();

        for (Study study : studies) {
            study.changeStatus(now);
        }
    }

    public void updateStudy(Long memberId, Long studyId, StudyRequest request) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        final Content content = request.mapToContent();
        study.updatePlanners(LocalDate.now(), request.getMaxMemberCount(),
                request.getEnrollmentEndDate(), request.getStartDate(), request.getEndDate());
        study.updateContent(memberId, content, request.mapToAttachedTags());
    }
}

