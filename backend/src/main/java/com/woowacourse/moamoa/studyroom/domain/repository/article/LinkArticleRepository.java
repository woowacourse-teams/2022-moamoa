package com.woowacourse.moamoa.studyroom.domain.repository.article;

import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import org.springframework.data.jpa.repository.JpaRepository;

interface LinkArticleRepository extends JpaRepository<LinkArticle, Long>, ArticleRepository<LinkArticle> {
}
