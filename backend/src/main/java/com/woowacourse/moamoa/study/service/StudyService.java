package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
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
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
        final RecruitPlanner recruitPlanner = request.mapToRecruitPlan();

        final StudyPlanner studyPlanner = request.mapToStudyPlanner(createdAt.toLocalDate());
        final AttachedTags attachedTags = request.mapToAttachedTags();
        final Content content = request.mapToContent();

        return studyRepository.save(new Study(content, participants, recruitPlanner, studyPlanner, attachedTags, createdAt));
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

    public void updateStudy(Long githubId, Long studyId, CreatingStudyRequest request) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
        Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);

        checkOwner(study, member);
        study.update(request);
    }

    private void checkOwner(Study study, Member member) {
        if (!Objects.equals(study.getParticipants().getOwnerId(), member.getId())) {
            throw new UnauthorizedException("스터디 방장만이 스터디를 수정할 수 있습니다.");
        }
    }
}
