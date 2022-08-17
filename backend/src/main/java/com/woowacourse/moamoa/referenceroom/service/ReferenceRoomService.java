package com.woowacourse.moamoa.referenceroom.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.referenceroom.domain.Author;
import com.woowacourse.moamoa.referenceroom.domain.Link;
import com.woowacourse.moamoa.referenceroom.domain.repository.LinkRepository;
import com.woowacourse.moamoa.referenceroom.service.exception.LinkNotFoundException;
import com.woowacourse.moamoa.referenceroom.service.exception.NotCreatingLinkException;
import com.woowacourse.moamoa.referenceroom.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.referenceroom.service.request.LinkRequest;
import com.woowacourse.moamoa.review.domain.AssociatedStudy;
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

    public Link createLink(final Long githubId, final Long studyId, final LinkRequest linkRequest) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        if (!study.isParticipant(member.getId())) {
            throw new NotCreatingLinkException();
        }

        final Link link = linkRequest.toLink(studyId, member.getId());
        return linkRepository.save(link);
    }

    public void updateLink(
            final Long githubId, final Long studyId, final Long linkId, final LinkRequest linkRequest
    ) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Link link = linkRepository.findById(linkId)
                .orElseThrow(LinkNotFoundException::new);

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        final Link updatedLink = linkRequest.toLink(studyId, member.getId());
        link.update(updatedLink);
    }

    public void deleteLink(final Long githubId, final Long studyId, final Long linkId) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Link link = linkRepository.findById(linkId)
                .orElseThrow(LinkNotFoundException::new);

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        link.delete(new AssociatedStudy(studyId), new Author(member.getId()));
    }
}
