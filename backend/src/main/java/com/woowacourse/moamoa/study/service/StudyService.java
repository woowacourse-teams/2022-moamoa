package com.woowacourse.moamoa.study.service;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Content;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.RecruitPlanner;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.StudyPlanner;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
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

    public Study createStudy(final Long githubId, final CreatingStudyRequest request) {
        final LocalDateTime createdAt = dateTimeSystem.now();
        final Member owner = findMemberBy(githubId);

        final Participants participants = request.mapToParticipants(owner.getId());
        final RecruitPlanner recruitPlanner = getRecruitPlanner(request, participants);

        final StudyPlanner studyPlanner = request.mapToStudyPlanner(createdAt.toLocalDate());
        final AttachedTags attachedTags = request.mapToAttachedTags();
        final Content content = request.mapToContent();

        return studyRepository.save(new Study(content, participants, recruitPlanner, studyPlanner, attachedTags, createdAt));
    }

    private static RecruitPlanner getRecruitPlanner(final CreatingStudyRequest request, final Participants participants) {
        final RecruitPlanner temporaryRecruitPlanner = request.mapToRecruitPlan();
        if (request.getMaxMemberCount() != null && participants.getSize() == temporaryRecruitPlanner.getMax()) {
            return new RecruitPlanner(temporaryRecruitPlanner.getMax(), RECRUITMENT_END, temporaryRecruitPlanner.getEnrollmentEndDate());
        }
        return temporaryRecruitPlanner;
    }

    public void participateStudy(final Long githubId, final Long studyId) {
        final Member member = findMemberBy(githubId);
        final Study study = findStudyBy(studyId);
        study.participate(member.getId());
    }

    private Study findStudyBy(final Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
    }

    private Member findMemberBy(final Long githubId) {
        return memberRepository.findByGithubId(githubId)
                .orElseThrow(() -> new UnauthorizedException(String.format("%d의 githubId를 가진 사용자는 없습니다.", githubId)));
    }

    public void autoUpdateStatus() {
        final List<Study> studies = studyRepository.findAll();
        final LocalDate now = dateTimeSystem.now().toLocalDate();

        for (Study study : studies) {
            study.changeStatus(now);
        }
    }
}
