package com.woowacourse.moamoa.community.service;

import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.query.CommunityArticleDao;
import com.woowacourse.moamoa.community.query.data.CommunityArticleData;
import com.woowacourse.moamoa.community.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.community.service.exception.NotArticleAuthorException;
import com.woowacourse.moamoa.community.service.exception.NotRelatedArticleException;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.community.service.response.ArticleResponse;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
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
    private final CommunityArticleDao communityArticleDao;

    public CommunityArticleService(final MemberRepository memberRepository,
                                   final StudyRepository studyRepository,
                                   final CommunityArticleRepository communityArticleRepository,
                                   final CommunityArticleDao communityArticleDao) {
        this.memberRepository = memberRepository;
        this.studyRepository = studyRepository;
        this.communityArticleRepository = communityArticleRepository;
        this.communityArticleDao = communityArticleDao;
    }

    @Transactional
    public CommunityArticle createArticle(final Long githubId, final Long studyId,
                                          final ArticleRequest request) {
        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        return communityArticleRepository.save(CommunityArticle.write(member, study, request));
    }

    public ArticleResponse getArticle(final Long githubId, final Long studyId, final Long articleId) {
        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        final CommunityArticle article = communityArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        if (!article.isBelongTo(study.getId())) {
            throw new NotRelatedArticleException(study.getId(), article.getId());
        }

        final CommunityArticleData data = communityArticleDao.getById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
        return new ArticleResponse(data);
    }

    @Transactional
    public void deleteArticle(final Long githubId, final Long studyId, final Long articleId) {
        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        final CommunityArticle article = communityArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        if (!article.isBelongTo(study.getId())) {
            throw new NotRelatedArticleException(study.getId(), article.getId());
        }

        if (!article.isAuthor(member.getId())) {
            throw new NotArticleAuthorException(article.getId(), member.getId());
        }

        communityArticleRepository.deleteById(articleId);
    }
}
