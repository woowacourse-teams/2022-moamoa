package com.woowacourse.moamoa.study;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaThumbnailRepository extends JpaRepository<ThumbnailImage, Long>, ThumbnailRepository {
}
