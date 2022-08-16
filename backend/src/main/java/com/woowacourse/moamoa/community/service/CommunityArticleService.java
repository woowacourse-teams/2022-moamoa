package com.woowacourse.moamoa.community.service;

import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.query.CommunityArticleDao;
import com.woowacourse.moamoa.community.query.data.CommunityArticleData;
import com.woowacourse.moamoa.community.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.community.service.exception.UneditableArticleException;
import com.woowacourse.moamoa.community.service.exception.UnviewableArticleException;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.community.service.response.ArticleResponse;
import com.woowacourse.moamoa.community.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.community.service.response.ArticleSummaryResponse;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public CommunityArticle createArticle(final Long memberId, final Long studyId,
                                          final ArticleRequest request) {
        final Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        return communityArticleRepository.save(CommunityArticle.write(member, study, request));
    }

    public ArticleResponse getArticle(final Long memberId, final Long studyId, final Long articleId) {
        final CommunityArticle article = communityArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isViewableBy(studyId, memberId)) {
            throw new UnviewableArticleException(studyId, memberId);
        }

        final CommunityArticleData data = communityArticleDao.getById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
        return new ArticleResponse(data);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId) {
        final CommunityArticle article = communityArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isEditableBy(studyId, memberId)) {
            throw new UneditableArticleException();
        }

        communityArticleRepository.deleteById(articleId);
    }

    public ArticleSummariesResponse getArticles(final Long memberId, final Long studyId, final Pageable pageable) {
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        if (!study.isParticipantOrOwner(memberId)) {
            throw new UnviewableArticleException(studyId, memberId);
        }

        final Page<CommunityArticleData> page = communityArticleDao.getAllByStudyId(studyId, pageable);

        final List<ArticleSummaryResponse> articles = page.getContent().stream()
                .map(ArticleSummaryResponse::new)
                .collect(Collectors.toList());

        return new ArticleSummariesResponse(articles, page.getNumber(), page.getTotalPages() - 1, page.getTotalElements());
    }

    @Transactional
    public void updateArticle(final Long memberId, final Long studyId, final Long articleId,
                              final ArticleRequest request) {
        final CommunityArticle article = communityArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isEditableBy(studyId, memberId)) {
            throw new UneditableArticleException();
        }

        article.update(request.getTitle(), request.getContent());
    }
}
