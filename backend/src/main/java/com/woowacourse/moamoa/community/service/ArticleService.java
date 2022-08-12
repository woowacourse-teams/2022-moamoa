package com.woowacourse.moamoa.community.service;

import com.woowacourse.moamoa.community.domain.Article;
import com.woowacourse.moamoa.community.query.ArticleDao;
import com.woowacourse.moamoa.community.query.data.ArticleData;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final ArticleDao articleDao;
    private final ArticleRepositoryFactory factory;

    @Autowired
    public ArticleService(final MemberRepository memberRepository,
                          final StudyRepository studyRepository,
                          final ArticleDao articleDao,
                          ArticleRepositoryFactory factory) {
        this.memberRepository = memberRepository;
        this.studyRepository = studyRepository;
        this.articleDao = articleDao;
        this.factory = factory;
    }

    @Transactional
    public Article createArticle(final Long memberId, final Long studyId,
                                 final ArticleRequest request, final String articleType) {
        final Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        return factory.getRepository(articleType).save(Article.write(member, study, request, articleType));
    }

    public ArticleResponse getArticle(final Long memberId, final Long studyId, final Long articleId,
                                      final String articleType) {
        final Article article = factory.getRepository(articleType).findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isViewableBy(studyId, memberId)) {
            throw new UnviewableArticleException(studyId, memberId);
        }

        final ArticleData data = articleDao.getById(articleId, articleType)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
        return new ArticleResponse(data);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId,
                              final String articleType) {
        final Article article = factory.getRepository(articleType).findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isEditableBy(studyId, memberId)) {
            throw new UneditableArticleException();
        }

        factory.getRepository(articleType).deleteById(articleId);
    }

    public ArticleSummariesResponse getArticles(final Long memberId, final Long studyId, final Pageable pageable,
                                                String articleType) {
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        if (!study.isParticipant(memberId)) {
            throw new UnviewableArticleException(studyId, memberId);
        }

        final Page<ArticleData> page = articleDao.getAllByStudyId(studyId, pageable, articleType);

        final List<ArticleSummaryResponse> articles = page.getContent().stream()
                .map(ArticleSummaryResponse::new)
                .collect(Collectors.toList());

        return new ArticleSummariesResponse(articles, page.getNumber(), page.getTotalPages() - 1, page.getTotalElements());
    }

    @Transactional
    public void updateArticle(final Long memberId, final Long studyId, final Long articleId,
                              final ArticleRequest request, final String articleType) {
        final Article article = factory.getRepository(articleType).findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isEditableBy(studyId, memberId)) {
            throw new UneditableArticleException();
        }

        article.update(request.getTitle(), request.getContent());
    }
}
