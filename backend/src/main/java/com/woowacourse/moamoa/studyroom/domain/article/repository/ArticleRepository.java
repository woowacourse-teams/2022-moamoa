package com.woowacourse.moamoa.studyroom.domain.article.repository;

import com.woowacourse.moamoa.studyroom.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
