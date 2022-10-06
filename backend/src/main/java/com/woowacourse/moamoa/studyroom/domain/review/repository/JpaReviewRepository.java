package com.woowacourse.moamoa.studyroom.domain.review.repository;

import com.woowacourse.moamoa.studyroom.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReviewRepository extends JpaRepository<Review, Long>, ReviewRepository {
}
