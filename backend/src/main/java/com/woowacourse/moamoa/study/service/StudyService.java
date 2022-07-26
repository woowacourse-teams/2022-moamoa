package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Details;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.Period;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;

    public Study createStudy(final Long githubId, final CreatingStudyRequest request) {
        final Member owner = findMemberBy(githubId);

        final Participants participants = request.mapToParticipants(owner.getId());
        final Details details = request.mapToDetails();
        final Period period = request.mapToPeriod();
        final AttachedTags attachedTags = request.mapToAttachedTags();

        return studyRepository.save(new Study(details, participants, period, attachedTags));
    }

    @Transactional
    public void participantStudy(final Long githubId, final Long studyId) {
        final Member member = findMemberBy(githubId);
        final Study study = findStudyBy(studyId);

        study.checkStudyParticipating(member.getId());
    }

    private Study findStudyBy(final Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
    }

    private Member findMemberBy(final Long githubId) {
        return memberRepository.findByGithubId(githubId)
                .orElseThrow(() -> new UnauthorizedException(String.format("%d의 githubId를 가진 사용자는 없습니다.", githubId)));
    }
}
