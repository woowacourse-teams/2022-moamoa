package com.woowacourse.moamoa.study.service;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;

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

    public Study createStudy(final Long githubId, final StudyRequest request) {
        final LocalDateTime createdAt = dateTimeSystem.now();
        final Member owner = findMemberBy(githubId);

        final Participants participants = request.mapToParticipants(owner.getId());
        final RecruitPlanner recruitPlanner = getRecruitPlan(participants.getSize(), request, createdAt);

        final StudyPlanner studyPlanner = request.mapToStudyPlanner(createdAt.toLocalDate());
        final AttachedTags attachedTags = request.mapToAttachedTags();
        final Content content = request.mapToContent();

        return studyRepository.save(
                new Study(content, participants, recruitPlanner, studyPlanner, attachedTags, createdAt));
    }

    private RecruitPlanner getRecruitPlan(final Integer participantsSize, final StudyRequest studyRequest,
                                          final LocalDateTime now) {

        if (studyRequest.getEnrollmentEndDate() == null) {
            return studyRequest.mapToRecruitPlan(RECRUITMENT_START);
        }

        if (studyRequest.getEnrollmentEndDate() != null && studyRequest.getEnrollmentEndDate().isBefore(now.toLocalDate())) {
            return studyRequest.mapToRecruitPlan(RECRUITMENT_END);
        }

        if (studyRequest.getMaxMemberCount() == null) {
            return studyRequest.mapToRecruitPlan(RECRUITMENT_START);
        }

        if (studyRequest.getMaxMemberCount() > participantsSize) {
            return studyRequest.mapToRecruitPlan(RECRUITMENT_START);
        }

        if (studyRequest.getEnrollmentEndDate().isBefore(now.toLocalDate()) || studyRequest.getMaxMemberCount() <= participantsSize) {
            return studyRequest.mapToRecruitPlan(RECRUITMENT_END);
        }

        throw new RuntimeException("스터디 모집 상태에서 오류가 발생했습니다.");
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

    public void updateStudy(Long memberId, Long studyId, StudyRequest request) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        final Content content = request.mapToContent();
        final RecruitPlanner recruitPlanner = getRecruitPlan(study.getParticipants().getSize(), request,
                dateTimeSystem.now());
        final StudyPlanner studyPlanner = request.mapToStudyPlanner(LocalDate.now());

        study.update(memberId, content, recruitPlanner, request.mapToAttachedTags(), studyPlanner);
    }
}
