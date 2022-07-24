package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.controller.request.OpenStudyRequest;
import com.woowacourse.moamoa.study.domain.AttachedTag;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Details;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.Period;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import java.util.List;
import java.util.stream.Collectors;
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

    public Study createStudy(final Long githubId, final OpenStudyRequest openStudyRequest) {
        final List<AttachedTag> attachedTags = openStudyRequest.getTagIds()
                .stream()
                .map(AttachedTag::new)
                .collect(Collectors.toList());

        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(() -> new UnauthorizedException(""));
        final Participants participants = Participants.createByMaxSize(openStudyRequest.getMaxMemberCount(),
                member.getId());
        final Details details = new Details(openStudyRequest.getTitle(), openStudyRequest.getExcerpt(),
                openStudyRequest.getThumbnail(), "OPEN", openStudyRequest.getDescription());
        final Period period = new Period(openStudyRequest.getEnrollmentEndDateTime(),
                openStudyRequest.getStartDateTime(),
                openStudyRequest.getEndDateTime());

        return studyRepository.save(new Study(details, participants, period, new AttachedTags(attachedTags)));
    }

}
