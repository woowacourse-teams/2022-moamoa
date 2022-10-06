package com.woowacourse.moamoa.studyroom.domain.review.repository;

import com.woowacourse.moamoa.studyroom.domain.review.Review;
import java.util.Optional;

public interface ReviewRepository {

    Review save(Review review);

    Optional<Review> findById(Long id);

    void deleteById(Long id);
}
