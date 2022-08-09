package com.woowacourse.moamoa.community.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityArticleRepository {

    boolean existsById(Long id);
}
