package com.woowacourse.moamoa.studyroom.domain.repository.article;

import com.woowacourse.moamoa.studyroom.domain.article.CommunityArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityArticleRepository extends JpaRepository<CommunityArticle, Long> {
}
