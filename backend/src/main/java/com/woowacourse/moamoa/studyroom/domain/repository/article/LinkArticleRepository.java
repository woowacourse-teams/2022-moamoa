package com.woowacourse.moamoa.studyroom.domain.repository.article;

import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.linkarticle.LinkArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkArticleRepository extends JpaRepository<LinkArticle, Long>, ArticleRepository<LinkArticle> {

    @Override
    default boolean isSupportType(ArticleType articleType) {
        return articleType.equals(ArticleType.LINK);
    }
}
