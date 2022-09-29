package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractArticleService<A extends Article<C>, C extends Content<A>> {

    private final StudyRoomRepository studyRoomRepository;
    private final ArticleRepository<A> articleRepository;
    private final Class<A> articleType;

    protected AbstractArticleService(
            final StudyRoomRepository studyRoomRepository, final ArticleRepository<A> articleRepository,
            final Class<A> articleType
    ) {
        this.studyRoomRepository = studyRoomRepository;
        this.articleRepository = articleRepository;
        this.articleType = articleType;
    }

    public A createArticle(final Long memberId, final Long studyId, final ArticleRequest<C> articleRequest) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final C content = articleRequest.createContent();
        final A article = content.createArticle(studyRoom, new Accessor(memberId, studyId));

        return articleRepository.save(article);
    }

    public void updateArticle(
            final Long memberId, final Long studyId, final Long articleId, final ArticleRequest<C> articleRequest
    ) {
        final A article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, articleType));
        final C newContent = articleRequest.createContent();

        article.update(new Accessor(memberId, studyId), newContent);
    }

    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId) {
        final A article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, articleType));
        final Accessor accessor = new Accessor(memberId, studyId);

        article.delete(accessor);
    }
}
