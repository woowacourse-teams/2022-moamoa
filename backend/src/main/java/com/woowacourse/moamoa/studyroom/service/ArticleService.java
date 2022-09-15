package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.request.AbstractArticleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ArticleService<A extends Article<C>, C extends Content<A>> {

    private final StudyRoomRepository studyRoomRepository;
    private final JpaRepository<A, Long> articleRepository;

    public ArticleService(
            final StudyRoomRepository studyRoomRepository, final JpaRepository<A, Long> articleRepository
    ) {
        this.studyRoomRepository = studyRoomRepository;
        this.articleRepository = articleRepository;
    }

    public A createArticle(
            final Long memberId, final Long studyId, final AbstractArticleRequest<C> articleRequest
    ) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);

        final C content = articleRequest.mapToContent();
        final A article = content.createArticle(studyRoom, new Accessor(memberId, studyId));

        return articleRepository.save(article);
    }

    public void updateArticle(
            final Long memberId, final Long studyId, final Long articleId, final AbstractArticleRequest<C> articleRequest
    ) {
        final A article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.LINK));

        article.update(new Accessor(memberId, studyId), articleRequest.mapToContent());
    }

    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId) {
        final A article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId, ArticleType.LINK));

        final Accessor accessor = new Accessor(memberId, studyId);

        article.delete(accessor);
    }
}
