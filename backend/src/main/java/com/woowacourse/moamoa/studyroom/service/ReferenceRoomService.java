package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.repository.article.LinkArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.service.exception.LinkNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.studyroom.service.request.EditingLinkRequest;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReferenceRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final LinkArticleRepository linkArticleRepository;

    public ReferenceRoomService(
            final StudyRoomRepository studyRoomRepository,
            final MemberRepository memberRepository,
            final StudyRepository studyRepository,
            final LinkArticleRepository linkArticleRepository) {
        this.studyRoomRepository = studyRoomRepository;
        this.memberRepository = memberRepository;
        this.studyRepository = studyRepository;
        this.linkArticleRepository = linkArticleRepository;
    }

    public LinkArticle createLink(final Long memberId, final Long studyId, final LinkArticleRequest linkArticleRequest) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final LinkArticle linkArticle = studyRoom.writeLinkArticle(
                new Accessor(memberId, studyId), linkArticleRequest.getLinkUrl(), linkArticleRequest.getDescription());

        return linkArticleRepository.save(linkArticle);
    }

    public void updateLink(
            final Long memberId, final Long studyId, final Long linkId, final EditingLinkRequest editingLinkRequest
    ) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final LinkArticle linkArticle = linkArticleRepository.findById(linkId)
                .orElseThrow(LinkNotFoundException::new);

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        final LinkArticle updatedLinkArticle = studyRoom.writeLinkArticle(new Accessor(member.getId(), studyId),
                editingLinkRequest.getLinkUrl(), editingLinkRequest.getDescription());

        linkArticle.update(updatedLinkArticle);
    }

    public void deleteLink(final Long memberId, final Long studyId, final Long linkId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final LinkArticle linkArticle = linkArticleRepository.findById(linkId)
                .orElseThrow(LinkNotFoundException::new);

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        linkArticle.delete(member.getId(), studyId);
    }
}
