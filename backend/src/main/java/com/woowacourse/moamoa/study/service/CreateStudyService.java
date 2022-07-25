package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.service.request.CreateStudyRequest;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Details;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.Period;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateStudyService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;

    public CreateStudyService(final MemberRepository memberRepository,
                              final StudyRepository studyRepository) {
        this.memberRepository = memberRepository;
        this.studyRepository = studyRepository;
    }

    public Study createStudy(final Long githubId, final CreateStudyRequest request) {
        final Member owner = memberRepository.findByGithubId(githubId)
                .orElseThrow(() -> new UnauthorizedException(String.format("%d의 githubId를 가진 사용자는 없습니다.", githubId)));

        final Participants participants = request.mapToParticipants(owner.getId());
        final Details details = request.mapToDetails();
        final Period period = request.mapToPeriod();
        final AttachedTags attachedTags = request.mapToAttachedTags();

        return studyRepository.save(new Study(details, participants, period, attachedTags));
    }

}
