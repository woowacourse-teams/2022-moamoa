package com.woowacourse.moamoa.review.domain.repository;

import com.woowacourse.moamoa.review.domain.Review;
import java.util.Optional;

public interface ReviewRepository {

    Review save(Review review);

    Optional<Review> findById(Long id);

    void deleteById(Long id);
}
