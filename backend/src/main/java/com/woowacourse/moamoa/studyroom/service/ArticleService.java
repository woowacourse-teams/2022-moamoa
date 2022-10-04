package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.article.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.ArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final StudyRoomRepository studyRoomRepository;
    private final ArticleRepository articleRepository;
    private final ArticleDao articleDao;

    public ArticleService(final StudyRoomRepository studyRoomRepository,
                          ArticleRepository articleRepository,
                          final ArticleDao articleDao) {
        this.studyRoomRepository = studyRoomRepository;
        this.articleRepository = articleRepository;
        this.articleDao = articleDao;
    }

    public ArticleResponse getArticle(final Long articleId, final ArticleType type) {
        final ArticleData data = articleDao.getById(articleId, type)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, Article.class));
        return new ArticleResponse(data);
    }

    public ArticleSummariesResponse getArticles(final Long studyId, final Pageable pageable, final ArticleType type) {
        final Page<ArticleData> page = articleDao.getAllByStudyId(studyId, pageable, type);

        final List<ArticleSummaryResponse> articles = page.getContent().stream()
                .map(ArticleSummaryResponse::new)
                .collect(Collectors.toList());

        return new ArticleSummariesResponse(articles, page.getNumber(), page.getTotalPages() - 1,
                page.getTotalElements());
    }

    @Transactional
    public Article createArticle(
            final Long memberId, final Long studyId, final Content content, final ArticleType type
    ) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(() -> new StudyNotFoundException(studyId));
        final Article article = Article.create(studyRoom, new Accessor(memberId, studyId), content, type);

        return articleRepository.save(article);
    }

    @Transactional
    public void updateArticle(
            final Long memberId, final Long studyId, final Long articleId, final Content newContent
    ) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, Article.class));

        article.update(new Accessor(memberId, studyId), newContent);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, Article.class));
        final Accessor accessor = new Accessor(memberId, studyId);

        article.delete(accessor);
    }
}
