package com.woowacourse.moamoa.studyroom.domain.article.repository;

import com.woowacourse.moamoa.studyroom.domain.article.TempArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempArticleRepository extends JpaRepository<TempArticle, Long> {
}
