package com.woowacourse.moamoa.studyroom.domain.repository;

import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.NoticeArticle;
import org.springframework.data.jpa.repository.JpaRepository;

interface NoticeArticleRepository extends JpaRepository<NoticeArticle, Long>, ArticleRepository<NoticeArticle> {

    @Override
    default boolean isSupportType(ArticleType articleType) {
        return articleType.equals(ArticleType.NOTICE);
    }
}
