package com.woowacourse.moamoa.review.domain.repository;

import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import com.woowacourse.moamoa.review.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReviewRepository extends JpaRepository<Review, Long>, ReviewRepository {

    List<Review> findAllByAssociatedStudy(AssociatedStudy associatedStudy);
}
