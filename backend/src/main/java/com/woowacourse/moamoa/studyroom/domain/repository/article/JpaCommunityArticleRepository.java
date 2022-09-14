package com.woowacourse.moamoa.studyroom.domain.repository.article;

import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.CommunityArticle;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaCommunityArticleRepository extends JpaRepository<CommunityArticle, Long>, ArticleRepository<CommunityArticle> {

    @Override
    default boolean isSupportType(ArticleType articleType) {
        return articleType.equals(ArticleType.COMMUNITY);
    }
}
