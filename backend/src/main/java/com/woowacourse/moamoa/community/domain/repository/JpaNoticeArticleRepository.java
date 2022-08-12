package com.woowacourse.moamoa.community.domain.repository;

import com.woowacourse.moamoa.community.domain.NoticeArticle;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaNoticeArticleRepository extends JpaRepository<NoticeArticle, Long> {
}
