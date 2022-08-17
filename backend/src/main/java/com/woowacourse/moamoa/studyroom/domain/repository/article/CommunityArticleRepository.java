package com.woowacourse.moamoa.studyroom.domain.repository.article;

import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.CommunityArticle;
import org.springframework.data.jpa.repository.JpaRepository;

interface CommunityArticleRepository extends JpaRepository<CommunityArticle, Long>, ArticleRepository<CommunityArticle> {

    @Override
    default boolean isSupportType(ArticleType articleType) {
        return articleType.equals(ArticleType.COMMUNITY);
    }
}
