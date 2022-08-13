package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.ArticleRepositoryFactory;
import com.woowacourse.moamoa.studyroom.query.ArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.UneditableArticleException;
import com.woowacourse.moamoa.studyroom.service.exception.UnviewableArticleException;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
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

    private final StudyRepository studyRepository;
    private final ArticleRepositoryFactory articleRepositoryFactory;
    private final ArticleDao articleDao;

    @Autowired
    public ArticleService(final StudyRepository studyRepository,
                          final ArticleRepositoryFactory articleRepositoryFactory,
                          final ArticleDao articleDao) {
        this.studyRepository = studyRepository;
        this.articleRepositoryFactory = articleRepositoryFactory;
        this.articleDao = articleDao;
    }

    @Transactional
    public Article createArticle(final Long memberId, final Long studyId,
                                 final ArticleRequest request, final ArticleType articleType) {
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        final ArticleRepository<Article> repository = articleRepositoryFactory.getRepository(articleType);
        return repository.save(Article.write(memberId, study, request.getTitle(), request.getContent(), articleType));
    }

    public ArticleResponse getArticle(final Long memberId, final Long studyId, final Long articleId,
                                      final ArticleType type) {
        final Article article = articleRepositoryFactory.getRepository(type)
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isViewableBy(new Accessor(memberId, studyId))) {
            throw new UnviewableArticleException(studyId, memberId);
        }

        final ArticleData data = articleDao.getById(articleId, type)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
        return new ArticleResponse(data);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId, final ArticleType type) {
        final Article article = articleRepositoryFactory.getRepository(type)
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isEditableBy(new Accessor(memberId, studyId))) {
            throw new UneditableArticleException();
        }

        articleRepositoryFactory.getRepository(type).deleteById(articleId);
    }

    public ArticleSummariesResponse getArticles(final Long memberId, final Long studyId, final Pageable pageable,
                                                final ArticleType type) {
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        if (!study.isParticipant(memberId)) {
            throw new UnviewableArticleException(studyId, memberId);
        }

        final Page<ArticleData> page = articleDao.getAllByStudyId(studyId, pageable, type);

        final List<ArticleSummaryResponse> articles = page.getContent().stream()
                .map(ArticleSummaryResponse::new)
                .collect(Collectors.toList());

        return new ArticleSummariesResponse(articles, page.getNumber(), page.getTotalPages() - 1,
                page.getTotalElements());
    }

    @Transactional
    public void updateArticle(final Long memberId, final Long studyId, final Long articleId,
                              final ArticleRequest request, final ArticleType type) {
        final Article article = articleRepositoryFactory.getRepository(type)
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        final Accessor accessor = new Accessor(memberId, studyId);

        if (!article.isEditableBy(accessor)) {
            throw new UneditableArticleException();
        }

        article.update(accessor, request.getTitle(), request.getContent());
    }
}
