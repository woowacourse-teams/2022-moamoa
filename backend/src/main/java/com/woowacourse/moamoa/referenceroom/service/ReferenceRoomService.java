package com.woowacourse.moamoa.referenceroom.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.referenceroom.domain.Link;
import com.woowacourse.moamoa.referenceroom.domain.repository.LinkRepository;
import com.woowacourse.moamoa.referenceroom.service.exception.NotCreatingLinkException;
import com.woowacourse.moamoa.referenceroom.service.request.CreatingLinkRequest;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReferenceRoomService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final LinkRepository linkRepository;

    public Long createLink(
            final Long githubId, final Long studyId, final CreatingLinkRequest creatingLinkRequest
    ) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        if (!study.isWritable(member.getId())) {
            throw new NotCreatingLinkException();
        }

        final Link link = creatingLinkRequest.toLink(studyId, member.getId());
        return linkRepository.save(link).getId();
    }
}
