package com.woowacourse.moamoa.community.domain.repository;

import com.woowacourse.moamoa.community.domain.CommunityArticle;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaCommunityArticleRepository extends JpaRepository<CommunityArticle, Long> {
}