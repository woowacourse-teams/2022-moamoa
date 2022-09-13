package com.woowacourse.moamoa.studyroom.domain.repository.article;

import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeArticle;
import org.springframework.data.jpa.repository.JpaRepository;

interface NoticeArticleRepository extends JpaRepository<NoticeArticle, Long>, ArticleRepository<NoticeArticle> {

    @Override
    default boolean isSupportType(ArticleType articleType) {
        return articleType.equals(ArticleType.NOTICE);
    }
}
