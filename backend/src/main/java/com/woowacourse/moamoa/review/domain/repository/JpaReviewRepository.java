package com.woowacourse.moamoa.review.domain.repository;

import com.woowacourse.moamoa.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReviewRepository extends JpaRepository<Review, Long>, ReviewRepository {
}
