package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.studyroom.query.LinkDao;
import com.woowacourse.moamoa.studyroom.query.data.LinkData;
import com.woowacourse.moamoa.studyroom.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchingLinkArticleService {

    private final LinkDao linkDao;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;

    public LinksResponse getLinks(final Long memberId, final Long studyId, final Pageable pageable) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        final Slice<LinkData> linkData = linkDao.findAllByStudyId(studyId, pageable);
        return new LinksResponse(linkData.getContent(), linkData.hasNext());
    }
}
