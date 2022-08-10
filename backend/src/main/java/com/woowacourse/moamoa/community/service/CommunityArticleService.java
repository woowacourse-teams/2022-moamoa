package com.woowacourse.moamoa.community.service;

import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommunityArticleService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final CommunityArticleRepository communityArticleRepository;

    public CommunityArticleService(final MemberRepository memberRepository,
                                   final StudyRepository studyRepository,
                                   final CommunityArticleRepository communityArticleRepository) {
        this.memberRepository = memberRepository;
        this.studyRepository = studyRepository;
        this.communityArticleRepository = communityArticleRepository;
    }

    @Transactional
    public CommunityArticle createArticle(final Long githubId, final Long studyId,
                                          final ArticleRequest request) {
        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        return communityArticleRepository.save(
                new CommunityArticle(request.getTitle(), request.getContent(), member.getId(), study.getId()));
    }
}
